package com.manos.prototype.controller.params;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProductOrderAndPageParams extends OrderAndPageParams{
	
	public static final String DATE_CREATED = "created";

	@NotNull
	@Pattern(regexp = DATE_CREATED )
	private String field = null;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
