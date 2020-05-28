package com.manos.prototype.dto;

import java.time.LocalDateTime;

public class ActivityDto {

	private Long id;

	private int actionId;
	
	private LocalDateTime date;
	
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
