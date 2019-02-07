package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProductRequestDto;

public interface ProductService {

	List<ProductDto> getProducts();
	
	ProductDto getProduct(int id);
	
	void deleteProduct(int id);
	
    void saveProduct(ProductRequestDto product);

	List<ProductDto> getProductsByProjectId(int id);
}
