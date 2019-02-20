package com.manos.prototype.dto;

public class ProjectDto {
	
	private int id;
	
	private String projectName;
	
	private String companyName;
	
	private String projectManager;

	private String date;

	private int statusId;
	
	private Long productsCount;

	public ProjectDto(int id, Long productsCount, String projectName, String companyName, String projectManager, String date, int statusId) {
		this.id = id;
		this.projectName = projectName;
		this.companyName = companyName;
		this.projectManager = projectManager;
		this.date = date;
		this.statusId = statusId;
		this.productsCount = productsCount;
	}
	
	public ProjectDto() {}
	
	public Long getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(Long productsCount) {
		this.productsCount = productsCount;
	}

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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
