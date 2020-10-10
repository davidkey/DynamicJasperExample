package com.dak.jasperpoc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dak.jasperpoc.exception.ReportException;
import com.dak.jasperpoc.reports.EmployeeReport;
import com.dak.jasperpoc.repository.EmployeeRepository;
import com.dak.jasperpoc.service.ReportService;

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

	@GetMapping
	public String getHome(){
		return "redirect:/employeeReport.pdf";
	}

	@GetMapping(value = "/employeeReport.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getEmployeeReportPdf(final HttpServletResponse response) throws ReportException {
		final EmployeeReport report = new EmployeeReport(employeeRepository.findAll());
		final byte[] data = reportService.getReportPdf(report.getReport());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=employeeReport.pdf");
		header.setContentLength(data.length);

		return new HttpEntity<byte[]>(data, header);
	}


	@GetMapping(value = "/employeeReport.xlsx", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	@ResponseBody
	public HttpEntity<byte[]> getEmployeeReportXlsx(final HttpServletResponse response) throws ReportException {
		final EmployeeReport report = new EmployeeReport(employeeRepository.findAll());
		final byte[] data = reportService.getReportXlsx(report.getReport());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=employeeReport.xlsx");
		header.setContentLength(data.length);

		return new HttpEntity<byte[]>(data, header);
	}
}
