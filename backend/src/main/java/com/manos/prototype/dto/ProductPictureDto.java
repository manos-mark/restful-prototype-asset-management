package com.manos.prototype.dto;

public class ProductPictureDto {

	private int id;
	
	private byte[] picture;
	
	private int productId;
	
	private boolean thumb;
	
	private String name;
	
	private Long size;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean isThumb() {
		return thumb;
	}

	public void setThumb(boolean thumb) {
		this.thumb = thumb;
	}
	
	
}
