package com.manos.prototype.converter;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.manos.prototype.entity.ProductPicture;
import com.pastelstudios.convert.Converter;

@Component
public class MultipartFileToProductPictureConverter implements Converter<CommonsMultipartFile, ProductPicture>{

	@Override
	public ProductPicture convert(CommonsMultipartFile from) {

		ProductPicture picture = new ProductPicture();
		
		picture.setPicture(from.getBytes());
		picture.setName(from.getOriginalFilename());
		picture.setSize(from.getSize());
		picture.setFileType(from.getContentType());
		
		return picture;
	}
}
