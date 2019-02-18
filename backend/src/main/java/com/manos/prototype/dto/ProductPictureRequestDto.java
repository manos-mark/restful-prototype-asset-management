package com.manos.prototype.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductPictureRequestDto {
	
	@NotNull
	private byte[] picture;
	
	@NotNull
	@Min(1)
	private int productId;
	
	@NotNull
	private boolean thumb;

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
