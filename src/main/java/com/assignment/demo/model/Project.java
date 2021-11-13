package com.assignment.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PROJECT")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_sequence")
	@SequenceGenerator(name = "project_sequence", sequenceName = "project_sequence")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "projects")
	@JsonIgnore
	private Set<Employee> employees;
	
	@ManyToMany(mappedBy = "projects")
	@JsonIgnore
	private Set<Department> departments;
	
	public void removeEmployee(Employee employee) {
		this.getEmployees().remove(employee);
		employee.getProjects().remove(this);
	}

	public void removeEmployees() {
		for (Employee employee : new HashSet<>(employees)) {
			removeEmployee(employee);
		}
	}
	
	public void removeDepartment(Department department) {
		this.getDepartments().remove(department);
		department.getProjects().remove(this);
	}

	public void removeDepartments() {
		for (Department department : new HashSet<>(departments)) {
			removeDepartment(department);
		}
	}
}