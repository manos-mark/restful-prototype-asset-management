package com.manos.prototype.controller.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProjectOrderAndPageParams extends OrderAndPageParams{

	public static final String DATE_CREATED = "created";
	public static final String PRODUCTS_COUNT = "products";
	
	@NotBlank
	@Pattern(regexp = DATE_CREATED + " | " + PRODUCTS_COUNT)
	private String field = null;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
