package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StatusRequestDto {
	
	@NotNull
	@Min(value=1)
	@Max(value=3)
	private int statusId;

	public StatusRequestDto(@NotNull @Min(1) @Max(3) int statusId) {
		this.statusId = statusId;
	}

	public StatusRequestDto() {
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
