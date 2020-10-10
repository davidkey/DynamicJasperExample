package com.dak.jasperpoc.reports;

import com.dak.jasperpoc.exception.ReportException;

import net.sf.jasperreports.engine.JasperPrint;

public interface Report {
	public JasperPrint getReport() throws ReportException;
}
