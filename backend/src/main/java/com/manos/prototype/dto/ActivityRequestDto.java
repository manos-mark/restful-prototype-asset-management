package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ActivityRequestDto {
	
	@NotNull
	@Min(value=1)
	@Max(value=8)
	private int actionId;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String date;
	
	public ActivityRequestDto(String date, int actionId) {
		this.date = date;
		this.actionId = actionId;
	}


	public ActivityRequestDto() {
	}

	public int getActionId() {
		return actionId;
	}
	
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
