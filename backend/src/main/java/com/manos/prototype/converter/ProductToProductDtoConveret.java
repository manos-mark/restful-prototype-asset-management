package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.entity.Product;
import com.pastelstudios.convert.Converter;

@Component
public class ProductToProductDtoConveret implements Converter<Product, ProductDto> {

	@Override
	public ProductDto convert(Product entity) {
		
		ProductDto dto = new ProductDto();
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setProductName(entity.getProductName());
		dto.setProjectName(entity.getProject().getProjectName());
		dto.setProjectId(entity.getProject().getId());
		dto.setQuantity(entity.getQuantity());
		dto.setSerialNumber(entity.getSerialNumber());
		dto.setStatusId(entity.getStatus().getId());
		dto.setThumbPictureId(entity.getThumbPicture().getId());
		return dto;
	}
	
}
