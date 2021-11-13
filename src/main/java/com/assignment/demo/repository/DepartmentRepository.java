package com.assignment.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.assignment.demo.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>  {

	public Department findByName(String departmentName);
}

