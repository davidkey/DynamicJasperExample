# DynamicJasperExample
[![Build Status](https://travis-ci.org/davidkey/DynamicJasperExample.svg?branch=master)](https://travis-ci.org/davidkey/DynamicJasperExample)

Short example of a dynamic JasperReport rendered as pdf / xlsx by a Spring Boot application. Uses [JasperReports Library 6.13](https://community.jaspersoft.com/project/jasperreports-library) and [DynamicJasper 5.3](http://dynamicjasper.com/) to generate reports.

## How to run
```bash
git clone https://github.com/davidkey/DynamicJasperExample.git
mvnw spring-boot:run
```
Once the application has started, you can go to one of the following urls to see the reports:
* http://localhost:8080/employeeReport.pdf
* http://localhost:8080/employeeReport.xlsx 

![pdf report screenshot](https://raw.githubusercontent.com/davidkey/DynamicJasperExample/master/screenshots/reportPdf.png "pdf report screenshot")
