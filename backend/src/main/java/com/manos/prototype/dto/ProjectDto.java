package com.manos.prototype.dto;

import java.time.LocalDate;

public class ProjectDto {

	private int id;

	private String projectName;

	private String companyName;

	private ProjectManagerDto projectManager;

	private LocalDate createdAt;

	private int statusId;

	private Long productsCount;

	public ProjectDto() {
	}

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

	public ProjectManagerDto getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManagerDto projectManager) {
		this.projectManager = projectManager;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
