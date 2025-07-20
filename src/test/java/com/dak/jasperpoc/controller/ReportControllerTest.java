package com.dak.jasperpoc.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dak.jasperpoc.repository.EmployeeRepository;
import com.dak.jasperpoc.service.TestDataService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
	
	@MockitoBean
	private EmployeeRepository employeeRepository;
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private TestDataService testDataService;

	public ReportControllerTest() {
	
	}
	
	@Test
	public void pdfShouldGenerate() throws Exception {	
		given(this.employeeRepository.findAll()).willReturn(testDataService.getTestEmployeeData());
		
		mvc.perform(get("/employeeReport.pdf")
			      .contentType(MediaType.APPLICATION_PDF))
			      .andExpect(status().isOk())
			      .andExpect(content().contentType("application/pdf"));
	}
	
	@Test
	public void xlsxShouldGenerate() throws Exception {
		given(this.employeeRepository.findAll()).willReturn(testDataService.getTestEmployeeData());
		
		mvc.perform(get("/employeeReport.xlsx")
			      .contentType(MediaType.APPLICATION_PDF))
			      .andExpect(status().isOk())
			      .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	}

}
