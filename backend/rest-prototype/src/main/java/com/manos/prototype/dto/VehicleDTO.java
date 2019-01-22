package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class VehicleDTO {
	
	@Min(value = 1, message = "must be greater than 0")
	private Integer id;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String make;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String model;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String color;
	
	@NotNull(message = "is required")
//	@Size(min = 1, message = "is required")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String dateOfProduction;
	
	@NotNull(message = "is required")
	@Min(value = 1, message = "must be greater than 0")
	@Max(value = 2, message = "must be equal or less than 500.000")
	private Double price;
	
	@NotNull(message = "is required")
	@Min(value = 1, message = "must be greater than 0")
	@Max(value = 2, message = "must be equal or less than 2")
	private Integer engineTypeId;
	
	@NotNull(message = "is required")
	@Min(value = 1, message = "must be greater than 0")
	private Integer autoshowroomId;
	
	@NotNull(message = "is required")
	@Min(value = 1, message = "must be greater than 0")
	private Integer buyerId;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDateOfProduction() {
		return dateOfProduction;
	}

	public void setDateOfProduction(String dateOfProduction) {
		this.dateOfProduction = dateOfProduction;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getEngineTypeId() {
		return engineTypeId;
	}

	public void setEngineTypeId(Integer engineTypeId) {
		this.engineTypeId = engineTypeId;
	}

	public Integer getAutoshowroomId() {
		return autoshowroomId;
	}

	public void setAutoshowroomId(Integer autoshowroomId) {
		this.autoshowroomId = autoshowroomId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
