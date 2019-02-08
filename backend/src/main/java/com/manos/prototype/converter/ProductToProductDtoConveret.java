package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.entity.Product;
import com.pastelstudios.convert.Converter;

@Component
public class ProductToProductDtoConveret implements Converter<Product, ProductDto> {

	@Override
	public ProductDto convert(Product entity) {
		
		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
	
}
