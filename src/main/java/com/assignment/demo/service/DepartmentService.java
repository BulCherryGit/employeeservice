package com.assignment.demo.service;

import java.util.List;

import com.assignment.demo.dto.DepartmentDto;

public interface DepartmentService {

	public DepartmentDto updateDepartment(Integer id, DepartmentDto course);

	public String deleteDepartment(Integer id);

	public DepartmentDto addDepartment(DepartmentDto courseDto);

	public List<DepartmentDto> getAllDepartments();

}
