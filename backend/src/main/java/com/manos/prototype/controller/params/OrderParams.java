package com.manos.prototype.controller.params;

import javax.validation.constraints.NotBlank;

public class OrderParams {

	@NotBlank
	private String field = null;
	
	@NotBlank
//	@Pattern(regexp= "asc | desc")
	private String direction = null;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
