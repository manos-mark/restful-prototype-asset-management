package com.manos.prototype.dto;

import java.util.List;

public class SearchDto {

	private List<SearchProductDto> products;
	
	private List<SearchProjectDto> projects;

	public List<SearchProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<SearchProductDto> products) {
		this.products = products;
	}

	public List<SearchProjectDto> getProjects() {
		return projects;
	}

	public void setProjects(List<SearchProjectDto> projects) {
		this.projects = projects;
	}

}
