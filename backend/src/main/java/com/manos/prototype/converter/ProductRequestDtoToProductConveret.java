package com.manos.prototype.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		
		// convert datetime
		String tempDate = productDto.getDate();
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy, HH:mm:ss");
		LocalDateTime date = LocalDateTime.parse(tempDate, formatter);
		
		product.setDate(date.toString());
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
