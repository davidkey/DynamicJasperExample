package com.dak.jasperpoc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dak.jasperpoc.model.Employee;
import com.dak.jasperpoc.reports.EmployeeReport;
import com.dak.jasperpoc.repository.EmployeeRepository;
import com.dak.jasperpoc.service.ReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/")
public class ReportController {

	private final EmployeeRepository employeeRepository;
	private final ReportService reportService;

	@Autowired
	public ReportController(final EmployeeRepository employeeRepository, final ReportService reportService){
		this.employeeRepository = employeeRepository;
		this.reportService = reportService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getHome(){
		return "redirect:/employeeReport.pdf";
	}

	@RequestMapping(value = "/employeeReport.pdf", method = RequestMethod.GET, produces = "application/pdf")
	public void getEmployeeReport(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		EmployeeReport report = new EmployeeReport(employeeRepository.findAll());
		JasperPrint jp = report.getReport();

		reportService.writePdfReport(jp, response, "employeeReport");
		return;
	}

	@PostConstruct
	@Transactional
	public void createTestEmployeeData(){
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

		employeeRepository.save(employees);
	}
}
