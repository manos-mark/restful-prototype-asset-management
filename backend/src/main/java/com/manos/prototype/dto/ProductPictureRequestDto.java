package com.manos.prototype.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProductPictureRequestDto {
	
	@NotNull
	private byte[] picture;
	
	@NotNull
	@Min(1)
	private int productId;
	
	@NotNull
	private boolean thumb;
	
	@NotNull
	@Length(min=2)
	private String name;
	
	@NotNull
	@Min(1)
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
