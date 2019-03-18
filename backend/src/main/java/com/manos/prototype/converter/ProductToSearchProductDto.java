package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.SearchProductDto;
import com.manos.prototype.entity.Product;
import com.pastelstudios.convert.Converter;

@Component
public class ProductToSearchProductDto implements Converter<Product, SearchProductDto>{

	@Override
	public SearchProductDto convert(Product from) {
		SearchProductDto to = new SearchProductDto();
		
		to.setId(from.getId());
		to.setProductName(from.getProductName());
		to.setSerialNumber(from.getSerialNumber());
		
		return to;
	}
	
}
