package com.manos.prototype.dto;

public class ProductDto {

	private int id;
	
	private String date;
	
	private String productName;
	
	private String serialNumber;
	
	private String description;
	
	private int quantity;
	
	private int statusId;
	
	private int projectId;
	
	private String projectName;
	
	private Long picturesCount;
	
	private int thumbPictureId;
	
	public ProductDto(String date, String productName, String serialNumber, String description, int quantity,
			int status, int project, String projectName, Long picturesCount) {
		this.date = date;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.description = description;
		this.quantity = quantity;
		this.statusId = status;
		this.projectId = project;
		this.picturesCount = picturesCount;
	}
	
	public ProductDto() {}

	
	public int getThumbPictureId() {
		return thumbPictureId;
	}

	public void setThumbPictureId(int thumbPictureId) {
		this.thumbPictureId = thumbPictureId;
	}

	public Long getPicturesCount() {
		return picturesCount;
	}

	public void setPicturesCount(Long picturesCount) {
		this.picturesCount = picturesCount;
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
