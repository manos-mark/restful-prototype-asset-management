package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.vo.ProductVo;
import com.pastelstudios.convert.Converter;

@Component
public class ProductVoToProductDtoConveret implements Converter<ProductVo, ProductDto> {

	@Override
	public ProductDto convert(ProductVo vo) {
		
		ProductDto dto = new ProductDto();
		dto.setCreatedAt(vo.getProduct().getCreatedAt());
		dto.setDescription(vo.getProduct().getDescription());
		dto.setId(vo.getProduct().getId());
		dto.setProductName(vo.getProduct().getProductName());
		dto.setProjectId(vo.getProduct().getProject().getId());
		dto.setProjectName(vo.getProduct().getProject().getProjectName());
		dto.setQuantity(vo.getProduct().getQuantity());
		dto.setSerialNumber(vo.getProduct().getSerialNumber());
		dto.setStatusId(vo.getProduct().getStatus().getId());
		dto.setThumbPictureId(vo.getProduct().getThumbPicture().getId());
		dto.setPicturesCount(vo.getPicturesCount());
		return dto;
	}
	
}
