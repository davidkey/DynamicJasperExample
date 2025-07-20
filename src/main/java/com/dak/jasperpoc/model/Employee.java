package com.dak.jasperpoc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity
@Data
@Builder
public class Employee {
	@Id
	private Integer empNo;
	private String name;    
	private Integer salary;
	private Float commission;

	@Tolerate
	public Employee(){}
}
