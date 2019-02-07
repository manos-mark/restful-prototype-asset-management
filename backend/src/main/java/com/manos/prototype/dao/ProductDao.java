package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Product;

public interface ProductDao {
	
	List<Product> getProducts();
	
	Product getProduct(int id);
	
	void deleteProduct(int id);
	
    void saveProduct(Product product);

	List<Product> getProductsByProjectId(int id);
}
