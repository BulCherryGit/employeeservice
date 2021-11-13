package com.assignment.demo.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.demo.model.Department;
import com.assignment.demo.model.Employee;
import com.assignment.demo.model.Project;

@SpringBootTest
public class PersistenceTest {

  @Autowired
  private EmployeeRepository employeeRepository;
  
  @Test
   void testSaveEmployee() {
    
    Employee emp = employeeRepository.save(getEmployee());
    
    assertTrue(emp != null);
    assertTrue(emp.getName().equals("JunitEmp"));
    
	assertTrue(emp.getDepartments().size() > 0);
	
	assertTrue(emp.getProjects().size() > 0);
  }
  
  private Employee getEmployee() {
    
	  Employee emp = new Employee();
	  emp.setName("JunitEmp");
	  
	  Set<Department> departments = new HashSet<>();
	  
	  Department dep = new Department();
	  dep.setName("JunitDepartment");
	  
	  departments.add(dep);
	  
	  
	  Set<Project> projects = new HashSet<>();
	  
	  Project proj = new Project();
	  proj.setName("JunitProj");
	  
	  projects.add(proj);
	  
	  emp.setDepartments(departments);
	  
	  emp.setProjects(projects);
   
    return emp;
  }
  
  
  @Test
  void testGetEmployee() {
   
   Employee emp = employeeRepository.findById(1).get();
   System.out.println("------------>>>>> "+emp.getId());
   assertTrue(emp != null);
   assertTrue(emp.getName().equals("JunitEmp"));
 }
}