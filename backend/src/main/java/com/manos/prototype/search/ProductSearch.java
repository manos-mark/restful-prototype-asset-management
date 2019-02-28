package com.manos.prototype.search;

import com.pastelstudios.db.GeneratedSearchConstraint;
import com.pastelstudios.db.SearchConstraint;

public class ProductSearch {

	private Integer statusId = null;
	
	private String dateRange = null;
	
	@GeneratedSearchConstraint
	public String getDateRangeSearchConstraint() {
		return dateRange;
	}
	
	public void setDateRangeSearchConstraint(String dateFrom, String dateTo) {
		if (dateFrom != null && dateTo != null) {
			this.dateRange = " project.date BETWEEN " + dateFrom + " AND " + dateTo;// emit the first AND
		}
		else if (dateFrom != null) {
			this.dateRange = " project.date >= " + dateFrom;
		} 
		else if (dateTo != null) {
			this.dateRange = " project.date <= " + dateTo;
		}
	}
	
//	@SearchConstraint(expression = "pStatus = ? ")
	@SearchConstraint(value = "projectStatus.id")
	public Integer getStatusIdSearchConstraint() {
		return statusId;
	}

	public void setStatusIdSearchConstraint(Integer statusId) {
		this.statusId = statusId;
	}

}
