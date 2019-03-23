package com.manos.prototype.controller.params;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

public class ProductFilterParams {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	
	@Min(0)
	@Max(3)
	private Integer statusId = null;
	
	@DateTimeFormat(pattern = DATE_FORMAT)
	private LocalDate fromDate = null;
	
	@DateTimeFormat(pattern = DATE_FORMAT)
	private LocalDate toDate = null;
	
	private String projectName = null;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getStatusId() {
		return statusId;
	}
	
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	
	public LocalDate getToDate() {
		return toDate;
	}
	
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
}
