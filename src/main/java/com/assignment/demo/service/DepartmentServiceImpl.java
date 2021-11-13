package com.assignment.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.demo.dto.DepartmentDto;

import com.assignment.demo.model.Department;
import com.assignment.demo.model.Employee;
import com.assignment.demo.model.Project;
import com.assignment.demo.repository.DepartmentRepository;
import com.assignment.demo.repository.EmployeeRepository;
import com.assignment.demo.repository.ProjectRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	private EmployeeRepository employeeRepository;

	@Resource
	private DepartmentRepository departmentRepository;
	
	@Resource
	private ProjectRepository projectRepository;

	@Transactional
	@Override
	public DepartmentDto addDepartment(DepartmentDto departmentDto) {
		Department department = new Department();
		mapDtoToEntity(departmentDto, department);
		Department savedDepartment = departmentRepository.save(department);
		return mapEntityToDto(savedDepartment);
	}

	@Override
	public List<DepartmentDto> getAllDepartments() {
		List<DepartmentDto> departmentDtos = new ArrayList<>();
		List<Department> departments = departmentRepository.findAll();
		departments.stream().forEach(department -> {
			DepartmentDto departmentDto = mapEntityToDto(department);
			departmentDtos.add(departmentDto);
		});
		return departmentDtos;
	}

	@Transactional
	@Override
	public DepartmentDto updateDepartment(Integer id, DepartmentDto departmentDto) {
		Department crs = departmentRepository.getOne(id);
		crs.getEmployees().clear();
		mapDtoToEntity(departmentDto, crs);
		Department department = departmentRepository.save(crs);
		return mapEntityToDto(department);
	}

	@Transactional
	@Override
	public String deleteDepartment(Integer id) {
		Optional<Department> department = departmentRepository.findById(id);
		// Remove the related employees from department entity.
		if(department.isPresent()) {
			department.get().removeEmployees();
			departmentRepository.deleteById(department.get().getId());
			return "Department with id: " + id + " deleted successfully!";
		}
		return null;
	}

	private void mapDtoToEntity(DepartmentDto departmentDto, Department department) {
		department.setName(departmentDto.getName());
		if (null == department.getEmployees()) {
			department.setEmployees(new HashSet<>());
		}
		
		if (null == department.getProjects()) {
			department.setProjects(new HashSet<>());
		}
		departmentDto.getEmployees().stream().forEach(employeeName -> {
			Employee employee = employeeRepository.findByName(employeeName);
			if (null == employee) {
				employee = new Employee();
				employee.setDepartments(new HashSet<>());
			}
			employee.setName(employeeName);
			employee.addDepartment(department);
		});
		
		
		departmentDto.getProjects().stream().forEach(projectName -> {
			Project project = projectRepository.findByName(projectName);
			if (null == project) {
				project = new Project();
				project.setDepartments(new HashSet<>());
			}
			project.setName(projectName);
			department.addProject(project);
		});
	}

	private DepartmentDto mapEntityToDto(Department department) {
		DepartmentDto responseDto = new DepartmentDto();
		responseDto.setName(department.getName());
		responseDto.setId(department.getId());
		responseDto.setEmployees(department.getEmployees().stream().map(Employee::getName).collect(Collectors.toSet()));
		
		responseDto.setProjects(department.getProjects().stream().map(Project::getName).collect(Collectors.toSet()));
		return responseDto;
	}
}
