package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.pastelstudios.convert.Converter;

@Component
public class ProductRequestDtoToProductConveret implements Converter<ProductRequestDto, Product> {

	@Override
	public Product convert(ProductRequestDto productDto) {
		
		Product product = new Product();
		product.setDate(productDto.getDate());
		product.setDescription(productDto.getDescription());
		product.setProductName(productDto.getProductName());
		product.setQuantity(productDto.getQuantity());
		product.setSerialNumber(productDto.getSerialNumber());
		
		int projectId = productDto.getProjectId();
		product.setProject(new Project(projectId));
		
		int statusId = productDto.getStatusId();
		product.setStatus(new Status(statusId));
		
		return product;
	}
	
}
