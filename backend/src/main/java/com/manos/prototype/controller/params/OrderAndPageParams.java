package com.manos.prototype.controller.params;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrderAndPageParams {

	@NotNull
	@Min(1)
	private Integer page = 1;

	@NotNull
	@Min(1)
	private Integer pageSize = 10;
	
	@NotBlank
	private String field = null;
	
	@NotBlank
	@Pattern(regexp= "asc | desc")
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
