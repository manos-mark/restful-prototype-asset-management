package com.manos.prototype.dto;

public class ActivityDto {

	private Long id;

//	private String action;
	
	private int actionId;
	
	private String date;
	
	public ActivityDto(Long id, String date, int actionId) {
		this.id = id;
		this.date = date;
		this.actionId = actionId;
	}


	public ActivityDto() {
	}

	public int getActionId() {
		return actionId;
	}
	
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
