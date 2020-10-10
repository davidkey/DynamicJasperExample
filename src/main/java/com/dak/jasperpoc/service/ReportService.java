package com.dak.jasperpoc.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dak.jasperpoc.exception.ReportException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Service used to generate binary report data (as byte[]) in various formats from a {@link net.sf.jasperreports.engine.JasperPrint JasperPrint} object.
 * @author davidkey
 */
@Service
public class ReportService {
	
	public ReportService() {
		// empty contructor
	}
	
	
	/**
	 * Returns pdf report as byte[]
	 * @param jasperPrint 
	 * @return byte[]
	 * @throws JRException
	 */
	public byte[] getReportPdf(final JasperPrint jasperPrint) throws ReportException {
		try {
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			throw new ReportException(e);
		}
	}
	
	/**
	 * Returns xlsx report as byte[]
	 * @param jasperPrint
	 * @return byte[]
	 * @throws JRException
	 * @throws IOException
	 */
	public byte[] getReportXlsx(final JasperPrint jasperPrint) throws ReportException {
		final JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		final byte[] rawBytes;
		
		try(final ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()){
			xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
			xlsxExporter.exportReport();

			rawBytes = xlsReport.toByteArray();
		} catch (JRException | IOException e) {
			throw new ReportException(e);
		}
		
		return rawBytes;
	}
}
