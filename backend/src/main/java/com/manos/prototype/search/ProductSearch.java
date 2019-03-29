package com.manos.prototype.search;

import java.time.LocalDateTime;

import com.pastelstudios.db.GeneratedSearchConstraint;
import com.pastelstudios.db.SearchConstraint;

public class ProductSearch {

	private Integer statusId = null;

	private LocalDateTime fromDate = null;
	private LocalDateTime toDate = null;
	
	private String projectName = null;
	
	@SearchConstraint(value = "project.projectName")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@SearchConstraint(value = "productStatus.id")
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
			return "product.createdAt BETWEEN :fromDate AND :toDate";// emit the first AND
		} else if (fromDate != null) {
			return "product.createdAt >= :fromDate";
		} else if (toDate != null) {
			return "product.createdAt <= :toDate";
		}
		return null;
	}
}
