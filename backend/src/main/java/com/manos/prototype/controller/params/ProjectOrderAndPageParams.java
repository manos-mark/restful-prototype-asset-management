package com.manos.prototype.controller.params;

import javax.validation.constraints.Pattern;

public class ProjectOrderAndPageParams extends OrderAndPageParams{

	public static final String DATE_CREATED = "date";
	public static final String PRODUCTS_COUNT = "products";
	
	@Pattern(regexp = DATE_CREATED)
	private String fieldDate = null;
	
	@Pattern(regexp = PRODUCTS_COUNT)
	private String fieldProductsCount = null;

	public String getFieldDate() {
		return fieldDate;
	}

	public void setFieldDate(String fieldDate) {
		this.fieldDate = fieldDate;
	}

	public String getFieldProductsCount() {
		return fieldProductsCount;
	}

	public void setFieldProductsCount(String fieldProductsCount) {
		this.fieldProductsCount = fieldProductsCount;
	}
	
	
}
