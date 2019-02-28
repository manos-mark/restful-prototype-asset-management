package com.manos.prototype.controller.params;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductFilterParams {

private Integer statusId = null;
	
	private String dateFrom = null;
	
	private String dateTo = null;

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		if ( !dateFrom.isEmpty() ) {
			// convert date
			String tempDate = dateFrom;
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(tempDate, formatter);
			
			this.dateFrom = date.toString();
		}
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		if ( !dateTo.isEmpty() ) {
			// convert date
			String tempDate = dateTo;
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(tempDate, formatter);
			
			this.dateTo = date.toString();
		}
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		if (statusId >= 1 && statusId <=3) {
			this.statusId = statusId;
		}
	}
}
