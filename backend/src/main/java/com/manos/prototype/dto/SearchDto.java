package com.manos.prototype.dto;

import java.util.List;

public class SearchDto {

	private List<ProductDto> products;
	
	private List<ProjectDto> projects;

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

	public List<ProjectDto> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDto> projects) {
		this.projects = projects;
	}
}
