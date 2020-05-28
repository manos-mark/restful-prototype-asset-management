package com.manos.prototype.vo;

import com.manos.prototype.entity.Product;

public class ProductVo {

	private Product product;
	
	private int picturesCount;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getPicturesCount() {
		return picturesCount;
	}

	public void setPicturesCount(int picturesCount) {
		this.picturesCount = picturesCount;
	}
	
}
