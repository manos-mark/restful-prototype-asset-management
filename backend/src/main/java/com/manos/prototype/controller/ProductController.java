package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.service.ProductService;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ConversionService conversionService;
	
	
//	@GetMapping()
//	public List<ProductDto> getProducts() {
//		List<Product> products = productService.getProducts();
//		return conversionService.convertList(products, ProductDto.class);
//		
//	}

	@GetMapping("/{projectId}")
	public List<ProductDto> getProducts(@PathVariable int projectId) {
		List<Product> products =  productService.getProductsByProjectId(projectId);
		return conversionService.convertList(products, ProductDto.class);
	}
	
	@DeleteMapping("/{productId}")
	public String deleteProduct(@PathVariable int productId) {
		return "Deleted user with id - " + productId;
	}
	
	@PutMapping
	public void updateProduct(@RequestBody ProductRequestDto productRequestDto) {
		Product product = conversionService.convert(productRequestDto, Product.class);
		productService.saveProduct(product);
	}
	
	@PostMapping
	public void addProduct(@RequestBody ProductRequestDto productRequestDto) {
		Product product = conversionService.convert(productRequestDto, Product.class);
		productService.saveProduct(product);
	}
	
}











