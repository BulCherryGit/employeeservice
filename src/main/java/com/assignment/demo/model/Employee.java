package com.assignment.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
	@SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "EMPLOYEE_DEPARTMENT", joinColumns = { @JoinColumn(name = "EMPLOYEE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "DEPARTMENT_ID") })
	private Set<Department> departments;
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "EMPLOYEE_PROJECT", joinColumns = { @JoinColumn(name = "EMPLOYEE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PROJECT_ID") })
	private Set<Project> projects;

	public void addDepartment(Department department) {
		this.departments.add(department);
		department.getEmployees().add(this);
	}

	public void removeDepartment(Department department) {
		this.getDepartments().remove(department);
		department.getEmployees().remove(this);
	}

	public void removeDepartments() {
		for (Department department : new HashSet<>(departments)) {
			removeDepartment(department);
		}
	}
	
	
	public void addProject(Project project) {
		this.projects.add(project);
		project.getEmployees().add(this);
	}

	public void removeProject(Project project) {
		this.getProjects().remove(project);
		project.getEmployees().remove(this);
	}
	
	public void removeProjects() {
		for (Project project : new HashSet<>(projects)) {
			removeProject(project);
		}
	}
}
