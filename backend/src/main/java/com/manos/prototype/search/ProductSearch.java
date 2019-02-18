package com.manos.prototype.search;

import com.pastelstudios.db.SearchConstraint;

public class ProductSearch {

	private Integer statusId = null;

//	@SearchConstraint(expression = "pStatus = ? ")
	@SearchConstraint(value = "pStatus.id")
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
}
