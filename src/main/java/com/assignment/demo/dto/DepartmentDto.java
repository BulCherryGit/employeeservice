package com.assignment.demo.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
	private Integer id;
	private String name;
	Set<String> employees = new HashSet<>();
	private Set<String> projects = new HashSet<>();
}
