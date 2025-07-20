package com.dak.jasperpoc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dak.jasperpoc.model.Employee;
import com.dak.jasperpoc.repository.EmployeeRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class TestDataService {

	private final EmployeeRepository employeeRepository;
	
	public TestDataService(final EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@PostConstruct
	@Transactional
	public void createTestEmployeeData(){
		employeeRepository.saveAll(getTestEmployeeData());
	}
	
	public List<Employee> getTestEmployeeData(){
		final String[] employeeNames = {"David Smith", "Mike Jones", "John Jackson", "Pierre Williams", "Bob Roberts"};

		final List<Employee> employees = new ArrayList<>(employeeNames.length);

		int employeeNumber = 100;
		for(String name : employeeNames){
			employees.add(Employee.builder()
					.empNo(employeeNumber)
					.commission((float)employeeNumber / 75f)
					.salary(employeeNumber * 888)
					.name(name)
					.build());

			employeeNumber++;
		}
		
		return employees;
	}
}
