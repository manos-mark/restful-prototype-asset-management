package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.entity.Product;

public interface ProductService {

//	List<Product> getProducts();
//	
//	Product getProduct(int id);
	
	void deleteProduct(int id);
	
    void saveProduct(Product product);

	List<Product> getProductsByProjectId(int id);

	Long getProductsCountByStatus(int statusId);

//	void updateProduct(Product product);

}
