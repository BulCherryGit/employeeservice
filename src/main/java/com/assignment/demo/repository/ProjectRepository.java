package com.assignment.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.demo.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>  {

	public Project findByName(String project);
}

