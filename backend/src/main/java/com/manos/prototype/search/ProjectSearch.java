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

	@GeneratedSearchConstraint
	public String getDateRangeSearchConstraint() {
		if (fromDate != null && toDate != null) {
			return "project.createdAt BETWEEN :fromDate AND :toDate";// emit the first AND
		} else if (fromDate != null) {
			return "project.createdAt >= :fromDate";
		} else if (toDate != null) {
			return "project.createdAt <= :toDate";
		}
		return null;
	}
}