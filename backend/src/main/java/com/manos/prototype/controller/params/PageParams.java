package com.manos.prototype.controller.params;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PageParams {

	@NotNull
	@Min(1)
	private Integer page = 1;

	@NotNull
	@Min(1)
	private Integer pageSize = 10;

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
