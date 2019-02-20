package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductPictureRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.pastelstudios.convert.Converter;

@Component
public class ProductPictureRequestDtoToProductPictureConverter implements Converter<ProductPictureRequestDto, ProductPicture> {

	@Override
	public ProductPicture convert(ProductPictureRequestDto dto) {
		ProductPicture entity = new ProductPicture();
		entity.setPicture(dto.getPicture());
		entity.setName(dto.getName());
		entity.setSize(dto.getSize());
		
		int productId = dto.getProductId();
		Product product = new Product(productId);
		
		entity.setProduct(product);
		
		return entity;
	}

}
