package com.manos.prototype.testservice;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.entity.Product;
import com.pastelstudios.db.GenericFinder;

@Service
public class ProductTestService {

	@Autowired
	private GenericFinder finder;
	
	@Transactional(readOnly = true)
	public Product getLastProduct() {
		List<Product> products = finder.findAll(Product.class);
		return products.get(products.size() - 1);
	}
	
	@Transactional(readOnly = true)
	public Product getProduct(int id) {
		return finder.findById(Product.class, id);
	}
	
}
