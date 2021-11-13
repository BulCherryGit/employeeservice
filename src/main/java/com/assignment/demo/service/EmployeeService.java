package com.assignment.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.assignment.demo.dto.EmployeeDto;

@Component
public interface EmployeeService {

	public EmployeeDto addEmployee(EmployeeDto employeeDto);

	public List<EmployeeDto> getAllEmployees();

	public EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employee);

	public String deleteEmployee(Integer employeeId);
	
	public Map<String, String> disassociateProjectFromEmployee(Integer employeeId, Integer projectId);
}
