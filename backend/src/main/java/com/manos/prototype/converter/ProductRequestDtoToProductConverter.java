package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Status;
import com.pastelstudios.convert.Converter;

@Component
public class ProductRequestDtoToProductConverter implements Converter<ProductRequestDto, Product> {

	@Override
	public Product convert(ProductRequestDto dto) {
		
		Product entity = new Product();
		
		entity.setDescription(dto.getDescription());
		entity.setProductName(dto.getProductName());
		entity.setQuantity(dto.getQuantity());
		entity.setSerialNumber(dto.getSerialNumber());
		entity.setStatus(new Status(dto.getStatusId()));
		
		return entity;
	}

}
