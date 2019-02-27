package com.manos.prototype.controller.params;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.lang.Nullable;

public class ProjectFilterParams {

	private Integer statusId = null;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		if (statusId >= 1 && statusId <=3) {
			this.statusId = statusId;
		}
	}
}
