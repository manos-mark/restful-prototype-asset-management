package com.manos.prototype.controller.params;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class StatusRequestParam {

	@Min(0)
	@Max(3)
	private Integer statusId;
	
	public StatusRequestParam(@Min(0) @Max(3) Integer statusId) {
		this.statusId = statusId;
	}

	public StatusRequestParam() {
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
}
