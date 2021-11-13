package com.assignment.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>  {

	public Employee findByName(String employeeName);
}

