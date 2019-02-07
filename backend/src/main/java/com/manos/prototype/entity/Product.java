package com.manos.prototype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "status_id")
	private int statusId;
	
	@Column(name = "project_id")
	private int projectId;

	public Product(String date, String productName, String serialNumber, String description, int quantity,
			int statusId, int projectId) {
		this.date = date;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.description = description;
		this.quantity = quantity;
		this.statusId = statusId;
		this.projectId = projectId;
	}
	
	public Product() {}

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
