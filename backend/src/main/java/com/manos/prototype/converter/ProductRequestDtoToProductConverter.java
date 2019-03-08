package com.manos.prototype.converter;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.pastelstudios.convert.Converter;

@Component
public class ProductRequestDtoToProductConverter implements Converter<ProductRequestDto, Product> {

	@Override
	public Product convert(ProductRequestDto dto) {
		
		Product entity = new Product();
		
		entity.setCreatedAt(LocalDate.now());
		entity.setDescription(dto.getDescription());
		entity.setProductName(dto.getProductName());
		entity.setQuantity(dto.getQuantity());
		entity.setSerialNumber(dto.getSerialNumber());
		entity.setStatus(new Status(Status.NEW_ID));
		entity.setProject(new Project(dto.getProjectId()));
		
		return entity;
	}

}
