package com.manos.prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.ProductService;
import com.manos.prototype.service.ProjectService;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ConversionService conversionService;
	
	@PostMapping("/count")
	public Long getProductsCount(@RequestBody StatusRequestDto statusId) {
		return productService.getProductsCountByStatus(statusId.getStatusId());
	}

	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int productId) {
		productService.deleteProduct(productId);
		return "Deleted user with id - " + productId;
	}
	
	@PutMapping("/{id}")
	public void updateProduct(@PathVariable("id") int productId, 
			@RequestBody ProductRequestDto productRequestDto) {
		Product product = conversionService.convert(productRequestDto, Product.class);
		product.setId(productId);
		
		// check if project exists
		int projectId = product.getProject().getId();
		Project project = projectService.getProject(projectId);
		
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + projectId);
		}
		
		productService.saveProduct(product);
	}
	
	@PostMapping
	public void addProduct(@RequestBody ProductRequestDto productRequestDto) {
		Product product = conversionService.convert(productRequestDto, Product.class);
		product.setId(0);
		productService.saveProduct(product);
	}
}











