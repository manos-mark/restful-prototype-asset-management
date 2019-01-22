package com.manos.prototype.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CarDTO extends VehicleDTO{
	
	@NotNull(message = "is required")
	@Min(value = 1, message = "must be greater than 0")
	@Max(value = 2, message = "must be equal or less than 2")
	private Integer carTypeId;

	public Integer getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(Integer carTypeId) {
		this.carTypeId = carTypeId;
	}
	
	
}
