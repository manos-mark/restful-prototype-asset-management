package com.manos.prototype.controller.params;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrderAndPageParams {

	public static final String DIRECTION_ASC = "asc";
	public static final String DIRECTION_DESC = "desc";
	
	@NotNull
	@Min(1)
	private Integer page = 1;

	@NotNull
	@Min(1)
	private Integer pageSize = 5;
	
	@NotNull
	@Pattern(regexp = "(" + DIRECTION_ASC + "|" + DIRECTION_DESC + ")" )
	private String direction = null;
	
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
