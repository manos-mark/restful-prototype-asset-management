package com.manos.prototype.dto;

public class SearchProductDto {

	private int id;
	
	private String productName;
	
	private String serialNumber;
	
	private String field;
	
	public SearchProductDto() {}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
