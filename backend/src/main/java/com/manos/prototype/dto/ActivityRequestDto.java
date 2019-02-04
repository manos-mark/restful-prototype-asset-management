package com.manos.prototype.dto;

public class ActivityRequestDto {

	private int actionId;
	
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
