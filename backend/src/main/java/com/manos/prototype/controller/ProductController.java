package com.manos.prototype.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.controller.params.ProductFilterParams;
import com.manos.prototype.controller.params.ProductOrderAndPageParams;
import com.manos.prototype.controller.params.ProjectFilterParams;
import com.manos.prototype.controller.params.ProjectOrderAndPageParams;
import com.manos.prototype.dto.PageResultDto;
import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.service.ProductServiceImpl;
import com.manos.prototype.service.ProjectServiceImpl;
import com.pastelstudios.convert.ConversionService;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private ProjectServiceImpl projectService;
	
	@Autowired
	private ConversionService conversionService;
	
	@GetMapping
	public PageResultDto<ProductDto> getProductsPaginated(@Valid ProductOrderAndPageParams pageParams,
			@Valid ProductFilterParams filterParams) {
		
		PageRequest pageRequest = conversionService.convert(pageParams, PageRequest.class);
		ProductSearch search = conversionService.convert(filterParams, ProductSearch.class);
		
		PageResult<Product> pageResult = productService.getProducts(pageRequest, search);
		
		List<ProductDto> productsDto = conversionService.convertList(pageResult.getEntities(), ProductDto.class);
		
		PageResultDto<ProductDto> pageResultDto = new PageResultDto<>();
		pageResultDto.setItems(productsDto);
		pageResultDto.setTotalCount(pageResult.getTotalCount());
		return pageResultDto;
	}
	
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











