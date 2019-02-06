package com.manos.prototype.dto;

import com.manos.prototype.entity.Status;

public class ProjectDto {
	
	private int id;
	
	private String projectName;
	
	private String companyName;
	
	private String projectManager;

	private String date;

	private Status status;

	public ProjectDto(int id, String projectName, String companyName, String projectManager, String date, Status status) {
		this.id = id;
		this.projectName = projectName;
		this.companyName = companyName;
		this.projectManager = projectManager;
		this.date = date;
		this.status = status;
	}
	
	public ProjectDto() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
