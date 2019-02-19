package com.manos.prototype.controller.params;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ProjectFilterParams {

	@Min(1)
	@Max(3)
	private Integer statusId = null;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
}
