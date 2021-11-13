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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DEPARTMENT")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
	@SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "departments")
	@JsonIgnore
	private Set<Employee> employees;
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "DEPARTMENT_PROJECT", joinColumns = { @JoinColumn(name = "DEPARTMENT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PROJECT_ID") })
	private Set<Project> projects;
	
	
	public void removeEmployee(Employee employee) {
		this.getEmployees().remove(employee);
		employee.getDepartments().remove(this);
	}

	public void removeEmployees() {
		for (Employee employee : new HashSet<>(employees)) {
			removeEmployee(employee);
		}
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
		project.getDepartments().add(this);
	}

	public void removeProject(Project project) {
		this.getProjects().remove(project);
		project.getDepartments().remove(this);
	}
	
	public void removeProjects() {
		for (Project project : new HashSet<>(projects)) {
			removeProject(project);
		}
	}
}