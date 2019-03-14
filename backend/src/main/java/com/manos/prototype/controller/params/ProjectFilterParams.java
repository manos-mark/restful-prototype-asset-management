package com.manos.prototype.controller.params;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

public class ProjectFilterParams {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Min(0)
	@Max(3)
	private Integer statusId = null;

	@DateTimeFormat(pattern = DATE_FORMAT)
	private LocalDate fromDate = null;

	@DateTimeFormat(pattern = DATE_FORMAT)
	private LocalDate toDate = null;

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
