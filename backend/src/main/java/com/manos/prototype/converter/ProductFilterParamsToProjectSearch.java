package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.controller.params.ProductFilterParams;
import com.manos.prototype.search.ProductSearch;
import com.pastelstudios.convert.Converter;

@Component
public class ProductFilterParamsToProjectSearch implements Converter<ProductFilterParams, ProductSearch>{

	@Override
	public ProductSearch convert(ProductFilterParams from) {
		ProductSearch productSearch = new ProductSearch();
		BeanUtils.copyProperties(from,productSearch);
		return productSearch;
	}

}
