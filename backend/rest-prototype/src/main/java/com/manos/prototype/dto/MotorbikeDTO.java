package com.manos.prototype.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MotorbikeDTO extends VehicleDTO{
	
	@NotNull(message = "is required")
	@DecimalMin(value = "0.1", message = "must be greater than 0.0")
	@DecimalMax(value = "5.0", message = "must be equal or less than 5.0")
	private Double tyreSize;
	
	@NotNull(message = "is required")
	@Min(value = 1, message = "must be greater than 0")
	@Max(value = 2, message = "must be equal or less than 2")
	private Integer motorbikeTypeId;

	public Double getTyreSize() {
		return tyreSize;
	}

	public void setTyreSize(Double tyreSize) {
		this.tyreSize = tyreSize;
	}

	public Integer getMotorbikeTypeId() {
		return motorbikeTypeId;
	}

	public void setMotorbikeTypeId(Integer motorbikeTypeId) {
		this.motorbikeTypeId = motorbikeTypeId;
	}
	
	
}
