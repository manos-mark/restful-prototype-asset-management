package com.manos.prototype.search;

import com.pastelstudios.db.GeneratedSearchConstraint;
import com.pastelstudios.db.SearchConstraint;

public class ProjectSearch {

	private Integer statusId = null;

//	@GeneratedSearchConstraint
//	public String getDateRangeSearchConstraint() {
//		return " a > 0 AND b > 1 "; // emit the first AND
//	}
	
//	@SearchConstraint(expression = "pStatus = ? ")
	@SearchConstraint(value = "pStatus.id")
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
}
