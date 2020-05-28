package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequestDto {
	
	@NotBlank
	private String productName;
	
	@NotBlank
	private String serialNumber;
	
	@NotBlank
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
	
	@NotNull
	@Min(-1)
	private int thumbPictureIndex;
	
	public ProductRequestDto() {}

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

	public int getThumbPictureIndex() {
		return thumbPictureIndex;
	}

	public void setThumbPictureIndex(int thumbPictureIndex) {
		this.thumbPictureIndex = thumbPictureIndex;
	}
}
