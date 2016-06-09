package com.dak.jasperpoc.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class ReportService {	
	
	public void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException{
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + (reportName == null ? jp.getName() : reportName).replace('"', '_') + ".pdf");

		OutputStream outStream = response.getOutputStream();
		//JasperExportManager.exportReportToPdfStream(jp, outStream); // since this doesn't send the content length, broken pipe errors occur w/ some browsers

		final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);
		response.setContentLength(pdfBytes.length);

		final ByteArrayInputStream bais = new ByteArrayInputStream(pdfBytes);
		IOUtils.copy(bais, outStream);

		outStream.flush();

		IOUtils.closeQuietly(bais);
		IOUtils.closeQuietly(outStream);
	}
	
	public void writeXlsxReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException{
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=" + (reportName == null ? jp.getName() : reportName).replace('"', '_') + ".xlsx");

		
		JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		
		
		ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
		
		xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
		xlsxExporter.exportReport();

		final byte[] rawBytes = xlsReport.toByteArray();
		response.setContentLength(rawBytes.length);

		final ByteArrayInputStream bais = new ByteArrayInputStream(rawBytes);
		
		final OutputStream outStream = response.getOutputStream();
		IOUtils.copy(bais, outStream);

		outStream.flush();

		IOUtils.closeQuietly(xlsReport);
		IOUtils.closeQuietly(bais);
		IOUtils.closeQuietly(outStream);
	}

}
