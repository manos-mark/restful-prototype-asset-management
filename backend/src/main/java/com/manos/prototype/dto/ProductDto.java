package com.manos.prototype.dto;

import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;

public class ProductDto {

	private int id;
	
	private String date;
	
	private String productName;
	
	private String serialNumber;
	
	private String description;
	
	private int quantity;
	
	private Status status;
	
	private Project project;
	
	public ProductDto(String date, String productName, String serialNumber, String description, int quantity,
			Status status, Project project) {
		this.date = date;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.description = description;
		this.quantity = quantity;
		this.status = status;
		this.project = project;
	}
	
	public ProductDto() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
