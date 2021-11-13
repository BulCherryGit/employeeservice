package com.assignment.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.demo.dto.EmployeeDto;
import com.assignment.demo.model.Department;
import com.assignment.demo.model.Employee;
import com.assignment.demo.model.Project;
import com.assignment.demo.repository.DepartmentRepository;
import com.assignment.demo.repository.EmployeeRepository;
import com.assignment.demo.repository.ProjectRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeRepository employeeRepository;

	@Resource
	private DepartmentRepository departmentRepository;
	
	@Resource
	private ProjectRepository projectRepository;

	@Transactional
	@Override
	public EmployeeDto addEmployee(EmployeeDto employeeDto) {
		Employee employee = new Employee();
		mapDtoToEntity(employeeDto, employee);
		Employee savedEmployee = employeeRepository.save(employee);
		return mapEntityToDto(savedEmployee);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> employeeDtos = new ArrayList<>();
		List<Employee> employees = employeeRepository.findAll();
		employees.stream().forEach(employee -> {
			EmployeeDto employeeDto = mapEntityToDto(employee);
			employeeDtos.add(employeeDto);
		});
		return employeeDtos;
	}

	@Transactional
	@Override
	public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto) {
		Employee std = employeeRepository.getOne(id);
		std.getDepartments().clear();
		mapDtoToEntity(employeeDto, std);
		Employee employee = employeeRepository.save(std);
		return mapEntityToDto(employee);
	}

	@Override
	public String deleteEmployee(Integer employeeId) {
		
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		//Remove the related departments from employee entity.
		if(employee.isPresent()) {
			employee.get().removeDepartments();
			employeeRepository.deleteById(employee.get().getId());
			return "Employee with id: " + employeeId + " deleted successfully!";
		}
		return null;
	}

	private void mapDtoToEntity(EmployeeDto employeeDto, Employee employee) {
		employee.setName(employeeDto.getName());
		if (null == employee.getDepartments()) {
			employee.setDepartments(new HashSet<>());
		}
		
		if (null == employee.getProjects()) {
			employee.setProjects(new HashSet<>());
		}
		employeeDto.getDepartments().stream().forEach(departmentName -> {
			Department department = departmentRepository.findByName(departmentName);
			if (null == department) {
				department = new Department();
				department.setEmployees(new HashSet<>());
			}
			department.setName(departmentName);
			employee.addDepartment(department);
		});
		
		employeeDto.getProjects().stream().forEach(projectName -> {
			Project project = projectRepository.findByName(projectName);
			if (null == project) {
				project = new Project();
				project.setEmployees(new HashSet<>());
			}
			project.setName(projectName);
			employee.addProject(project);
		});
	}

	private EmployeeDto mapEntityToDto(Employee employee) {
		EmployeeDto responseDto = new EmployeeDto();
		responseDto.setName(employee.getName());
		responseDto.setId(employee.getId());
		responseDto.setDepartments(employee.getDepartments().stream().map(Department::getName).collect(Collectors.toSet()));
		
		responseDto.setProjects(employee.getProjects().stream().map(Project::getName).collect(Collectors.toSet()));
		return responseDto;
	}

	@Override
	public Map<String, String> disassociateProjectFromEmployee(Integer employeeId, Integer projectId) {
		// TODO Auto-generated method stub
		Map<String, String>  response = new HashMap<>();
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		
		if (!employeeOptional.isPresent())
		{
			response.put("ERROR", "Employee does not exist with the given id "+employeeId);
			return response;
		}
		
		Employee employee = employeeOptional.get();
		
		Set<Project> projects = new HashSet(employee.getProjects());
		
		if (projects.size() < 2 )
		{
			response.put("ERROR", "Employee should be associated with atleast one project");
			return response;
		}
		boolean disconnected = false;
		for(Project project : projects)
		{
			if (project.getId().equals(projectId))
			{
				employee.getProjects().remove(project);
				disconnected = true;
				break;
			}
		}
		
	if (disconnected)
	{
		Employee emp = employeeRepository.save(employee);
		response.put("SUCCESS", "Employee is disconnected from the project ");
		return response;
	}
	else {
		response.put("ERROR", "Invalid projectId. ");
		return response;
	}
	}
}