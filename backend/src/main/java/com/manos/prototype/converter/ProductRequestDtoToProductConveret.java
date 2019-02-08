package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
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
		// pseudo-project, use it to pass the id into service
		int projectId = productDto.getProjectId();
		product.setProject(new Project(projectId));
		return product;
	}
	
}
