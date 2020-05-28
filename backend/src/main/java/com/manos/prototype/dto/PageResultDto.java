package com.manos.prototype.dto;

import java.util.List;

public class PageResultDto<ITEM_TYPE> {

	private List<ITEM_TYPE> items = null;
	private Integer totalCount = null;
	private List<String> projectNames = null;

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

	public List<String> getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(List<String> projectNames) {
		this.projectNames = projectNames;
	}
}
