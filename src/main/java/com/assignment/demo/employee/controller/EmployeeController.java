package com.assignment.demo.employee.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.demo.dto.EmployeeDto;
import com.assignment.demo.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@ApiOperation(value = "Retrieves the employees along with employee assoicated departments and projects")
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		List<EmployeeDto> employees = employeeService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

  @ApiOperation(value = "Adds an employee and associates the employee to one or more departments and projects", notes = "The "
  		+ "operation also creates the departments "
  		+ "and projects if they don't not exist and then associates the employee to the newly"
  		+ "created Department and/or Project. If the department or project exists already, employee will created "
  		+ "and associated to the existing department and project")
	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody EmployeeDto employeeDto) {
		
		if (employeeDto.getName() == null || employeeDto.getName().isEmpty())
		{
			return new ResponseEntity<>("Employee Name cannot be empty", HttpStatus.BAD_REQUEST);
		}
		
		if (employeeDto.getDepartments().isEmpty() || employeeDto.getProjects().isEmpty())
		{
			return new ResponseEntity<>("Atleast one department  and one project name should be associated with employee", HttpStatus.BAD_REQUEST);
		}
		
		EmployeeDto std = employeeService.addEmployee(employeeDto);
		return new ResponseEntity<>(std, HttpStatus.CREATED);
	}

//	@PutMapping("/employee/{id}")
//	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name = "id") Integer id,
//			@RequestBody EmployeeDto employee) {
//		EmployeeDto std = employeeService.updateEmployee(id, employee);
//		return new ResponseEntity<>(std, HttpStatus.CREATED);
//	}
	
	 @ApiOperation(value = "Disassociates the specified Project from  Employee", notes = "If the "
		  		+ "Employee is associated with only one project  "
		  		+ " or If employeeID or projectID does not exist, "
		  		+ "It returns an error.")
	@PutMapping("/employee/{employeeId}/project/{projectId}")
	public ResponseEntity<Object> disassociateProjectFromEmployee(@PathVariable(name = "employeeId") Integer employeeId,
			@PathVariable(name = "projectId") Integer projectId
			) {
		Map<String, String> response = employeeService.disassociateProjectFromEmployee(employeeId, projectId);
		
		if (response.containsKey("SUCCESS"))
			return new ResponseEntity<>(response, HttpStatus.OK);
		else
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

//	@DeleteMapping("/employee/{id}")
//	public ResponseEntity<String> deleteEmployee(@PathVariable(name = "id") Integer employeeId) {
//		String message = employeeService.deleteEmployee(employeeId);
//		return new ResponseEntity<>(message, HttpStatus.OK);
//	}
}