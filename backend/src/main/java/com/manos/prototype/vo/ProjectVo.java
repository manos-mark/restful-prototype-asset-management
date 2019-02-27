package com.manos.prototype.vo;

import com.manos.prototype.entity.Project;

public class ProjectVo {
	
	private Project project;
	
	private Long productsCount;

	public ProjectVo(Project project, Long productsCount) {
		this.project = project;
		this.productsCount = productsCount;
	}

	public ProjectVo() {
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(Long productsCount) {
		this.productsCount = productsCount;
	}
	
}
