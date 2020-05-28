package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ActivityRequestDto {
	
	@NotNull
	@Min(value=1)
	@Max(value=8)
	private int actionId;
	
	public ActivityRequestDto() {
	}

	public int getActionId() {
		return actionId;
	}
	
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

}
