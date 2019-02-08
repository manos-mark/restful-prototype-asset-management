package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProjectRequestDto {
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String projectName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String companyName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String projectManager;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String date;

	@NotNull
	@Min(value=1)
	@Max(value=3)
	private int statusId;

	public ProjectRequestDto(String projectName, String companyName, String projectManager, String date, int statusId) {
		this.projectName = projectName;
		this.companyName = companyName;
		this.projectManager = projectManager;
		this.date = date;
		this.statusId = statusId;
	}
	
	public ProjectRequestDto() {}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}