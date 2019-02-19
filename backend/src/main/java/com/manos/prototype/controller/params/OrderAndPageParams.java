package com.manos.prototype.controller.params;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class OrderAndPageParams {

	public static final String DIRECTION_ASC = "asc";
	public static final String DIRECTION_DESC = "desc";
	
	@Min(1)
	private Integer page = 1;

	@Min(1)
	private Integer pageSize = 100;
	
	@Pattern(regexp = DIRECTION_ASC )// + " | " + DIRECTION_DESC)
	private String directionAsc = "asc";
	
	@Pattern(regexp = DIRECTION_DESC )// + " | " + DIRECTION_DESC)
	private String directionDesc = null;

	public String getDirectionAsc() {
		return directionAsc;
	}

	public void setDirectionAsc(String directionAsc) {
		this.directionAsc = directionAsc;
	}

	public String getDirectionDesc() {
		return directionDesc;
	}

	public void setDirectionDesc(String directionDesc) {
		this.directionDesc = directionDesc;
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
