package com.dak.jasperpoc.reports;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import com.dak.jasperpoc.exception.ReportException;
import com.dak.jasperpoc.model.Employee;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Pojo used to hold report state and generate JasperReport object.
 * @author davidkey
 */
@SuppressWarnings("deprecation")
/*
 * SuppressWarnings for HorizontalAlign, VertialAlign constants
 * as they have been deprecated by DynamicJasper with no alternative or replacement mentioned.
 */
public class EmployeeReport implements Report {

	private final Collection<Employee> employees;

	public EmployeeReport(final Collection<Employee> employees) {
		this.employees = new ArrayList<>(employees);
	}

	public JasperPrint getReport() throws ReportException {
		final JasperPrint jp;
		try {
			Style headerStyle = createHeaderStyle();
			Style detailTextStyle = createDetailTextStyle();
			Style detailNumberStyle = createDetailNumberStyle();
			DynamicReport dynaReport = getReport(headerStyle, detailTextStyle, detailNumberStyle);
			jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
					new JRBeanCollectionDataSource(employees));
		} catch (JRException | ColumnBuilderException | ClassNotFoundException e) {
			throw new ReportException(e);
		}

		return jp;
	}

	private Style createHeaderStyle() {		
		return new StyleBuilder(true)
				.setFont(Font.VERDANA_MEDIUM_BOLD)
				.setBorder(Border.THIN())
				.setBorderBottom(Border.PEN_2_POINT())
				.setBorderColor(Color.BLACK)
				.setBackgroundColor(Color.ORANGE)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.CENTER)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setTransparency(Transparency.OPAQUE)
				.build();
	}

	private Style createDetailTextStyle() {
		return new StyleBuilder(true)
				.setFont(Font.VERDANA_MEDIUM)
				.setBorder(Border.DOTTED())
				.setBorderColor(Color.BLACK)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingLeft(5)
				.build();
	}

	private Style createDetailNumberStyle() {
		return new StyleBuilder(true)
				.setFont(Font.VERDANA_MEDIUM)
				.setBorder(Border.DOTTED())
				.setBorderColor(Color.BLACK)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.RIGHT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingRight(5)
				.setPattern("#,##0.00")
				.build();
	}

	private AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle, Style detailStyle)
			throws ColumnBuilderException {
		return ColumnBuilder.getNew()
				.setColumnProperty(property, type.getName())
				.setTitle(title)
				.setWidth(Integer.valueOf(width))
				.setStyle(detailStyle)
				.setHeaderStyle(headerStyle)
				.build();
	}

	private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();

		AbstractColumn columnEmpNo = createColumn("empNo", Integer.class, "Employee Number", 30, headerStyle, detailTextStyle);
		AbstractColumn columnName = createColumn("name", String.class, "Name", 30, headerStyle, detailTextStyle);
		AbstractColumn columnSalary = createColumn("salary", Integer.class, "Salary", 30, headerStyle, detailNumStyle);
		AbstractColumn columnCommission = createColumn("commission", Float.class, "Commission", 30, headerStyle, detailNumStyle);
		report.addColumn(columnEmpNo).addColumn(columnName).addColumn(columnSalary).addColumn(columnCommission);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(20, null, true));
		// you can also specify a font from the classpath, eg:
		// titleStyle.setFont(new Font(20, "/fonts/someFont.ttf", true));

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(Font.MEDIUM, null, true));

		report.setTitle("Employee Report");
		report.setTitleStyle(titleStyle.build());
		report.setSubtitle("Commission received by Employee");
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}
}