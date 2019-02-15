package com.manos.prototype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_pictures")
public class ProductPicture {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Lob
	@Column(name="picture", nullable=false, columnDefinition="mediumblob")
	private byte[] picture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
	
}
