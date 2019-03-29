package com.manos.prototype.search;

import java.time.LocalDateTime;

import com.pastelstudios.db.GeneratedSearchConstraint;
import com.pastelstudios.db.SearchConstraint;

public class ProjectSearch {

	private Integer statusId = null;

	private LocalDateTime fromDate = null;
	private LocalDateTime toDate = null;

	@SearchConstraint(value = "projectStatus.id")
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
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