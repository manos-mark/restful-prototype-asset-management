package com.manos.prototype.search;

import java.time.LocalDate;

import com.pastelstudios.db.GeneratedSearchConstraint;
import com.pastelstudios.db.SearchConstraint;

public class ProjectSearch {

	private Integer statusId = null;

	private LocalDate fromDate = null;
	private LocalDate toDate = null;

	@SearchConstraint(value = "projectStatus.id")
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
//		if (statusId != 0) {
			this.statusId = statusId;
//		}
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

	@GeneratedSearchConstraint
	public String getDateRangeSearchConstraint() {
		if (fromDate != null && toDate != null) {
			return "project.date BETWEEN :fromDate AND :toDate";// emit the first AND
		} else if (fromDate != null) {
			return "project.date >= :fromDate";
		} else if (toDate != null) {
			return "project.date <= :toDate";
		}
		return null;
	}
}