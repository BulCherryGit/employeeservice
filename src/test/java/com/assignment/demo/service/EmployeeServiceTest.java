package com.assignment.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.demo.dto.EmployeeDto;

@SpringBootTest
public class EmployeeServiceTest {
	
	@Autowired
	private EmployeeService  classUnderTest;
	
	@Test
	public  void testAddEmployee()
	{
		EmployeeDto empDto = classUnderTest.addEmployee(getEmployeeDTO());
		
		assertTrue(empDto != null);
		System.out.println("----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+empDto.getId());
		assertTrue(empDto.getName().equals("JunitEmp"));
		
		assertTrue(empDto.getDepartments().size() > 0);
		
		assertTrue(empDto.getProjects().size() > 0);
	}
	
	
	private EmployeeDto getEmployeeDTO() {
	    
		EmployeeDto empDto = new EmployeeDto();
		  empDto.setName("JunitEmp");
		  
		  Set<String> departments = new HashSet<>();
		  departments.add("JunitDepartment");
		  
		  
		  Set<String> projects = new HashSet<>();
		  
		
		  projects.add("JunitProj");
		  projects.add("JunitProj01");
		  
		  empDto.setDepartments(departments);
		  
		  empDto.setProjects(projects);
	   
	    return empDto;
	  }
	
	
	@Test
	public  void testGetEmployees()
	{
		
		List<EmployeeDto> empDto = classUnderTest.getAllEmployees();
		assertTrue(empDto != null);
		
	}
	
	
	@Test
	public  void testDisconnectEmployeeFromProject()
	{
		Map<String,String> errresponse = classUnderTest.disassociateProjectFromEmployee(1, 66);
		assertTrue(errresponse != null);
		assertTrue(errresponse.containsKey("ERROR"));
	}


}
