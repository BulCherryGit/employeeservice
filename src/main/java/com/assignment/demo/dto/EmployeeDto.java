package com.assignment.demo.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
	private Integer id;
	private String name;
	private Set<String> departments = new HashSet<>();
	private Set<String> projects = new HashSet<>();
}
