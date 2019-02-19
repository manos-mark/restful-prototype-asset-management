package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductPictureDto;
import com.manos.prototype.entity.ProductPicture;
import com.pastelstudios.convert.Converter;

@Component
public class ProductPictureToProductPictureDtoConverter implements Converter<ProductPicture, ProductPictureDto>{

	@Override
	public ProductPictureDto convert(ProductPicture from) {
		ProductPictureDto dto = new ProductPictureDto();
		dto.setId(from.getId());
		dto.setPicture(from.getPicture());
		dto.setProductId(from.getProduct().getId());
		dto.setThumb(from.isThumb());
		dto.setName(from.getName());
		dto.setSize(from.getSize());
		return dto;
	}

}
