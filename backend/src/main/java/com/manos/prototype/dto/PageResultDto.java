package com.manos.prototype.dto;

import java.util.List;

public class PageResultDto<ITEM_TYPE> {

	private List<ITEM_TYPE> items = null;
	private Integer totalCount = null;
	private Integer pageSize = null;// added

	public List<ITEM_TYPE> getItems() {
		return items;
	}

	public void setItems(List<ITEM_TYPE> items) {
		this.items = items;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
