package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductRequestDto {

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String date;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String productName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String serialNumber;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String description;
	
	@NotNull
	@Min(value=1)
	private int quantity;
	
	@NotNull
	@Min(value=1)
	@Max(value=3)
	private int statusId;
	
	@NotNull
	@Min(value=1)
	private int projectId;
	
	public ProductRequestDto(String date, String productName, String serialNumber, String description, int quantity,
			int statusId, int projectId) {
		this.date = date;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.description = description;
		this.quantity = quantity;
		this.statusId = statusId;
		this.projectId = projectId;
	}
	
	public ProductRequestDto() {}

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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
