package com.manos.prototype.dto;

import java.time.LocalDateTime;

public class ProductDto {

	private int id;
	
	private LocalDateTime createdAt;
	
	private String productName;
	
	private String serialNumber;
	
	private String description;
	
	private int quantity;
	
	private int statusId;
	
	private int projectId;
	
	private String projectName;
	
	private int picturesCount;
	
	private int thumbPictureId;
	
	public ProductDto() {}

	
	public int getThumbPictureId() {
		return thumbPictureId;
	}

	public void setThumbPictureId(int thumbPictureId) {
		this.thumbPictureId = thumbPictureId;
	}

	public int getPicturesCount() {
		return picturesCount;
	}

	public void setPicturesCount(int i) {
		this.picturesCount = i;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime date) {
		this.createdAt = date;
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
