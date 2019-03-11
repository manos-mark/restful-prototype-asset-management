package com.manos.prototype.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manos.prototype.controller.params.ProductFilterParams;
import com.manos.prototype.controller.params.ProductOrderAndPageParams;
import com.manos.prototype.dto.PageResultDto;
import com.manos.prototype.dto.PictureTypeRequestDto;
import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProductPictureDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Status;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.service.PictureServiceImpl;
import com.manos.prototype.service.ProductServiceImpl;
import com.manos.prototype.vo.ProductVo;
import com.pastelstudios.convert.ConversionService;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private PictureServiceImpl pictureService;

	@Autowired
	private ConversionService conversionService;

	@GetMapping
	public PageResultDto<ProductDto> getProductsPaginated(
			@Valid ProductOrderAndPageParams pageParams,
			@Valid ProductFilterParams filterParams) {

		PageRequest pageRequest = conversionService.convert(pageParams, PageRequest.class);
		ProductSearch search = conversionService.convert(filterParams, ProductSearch.class);

		PageResult<ProductVo> pageResult = productService.getProducts(pageRequest, search);

		List<ProductDto> productsDto = conversionService.convertList(pageResult.getEntities(), ProductDto.class);
		
		PageResultDto<ProductDto> pageResultDto = new PageResultDto<>();
		pageResultDto.setItems(productsDto);
		pageResultDto.setTotalCount(pageResult.getTotalCount());
		return pageResultDto;
	}

	@PostMapping("/count")
	public Long getProductsCountByStatus(@RequestBody StatusRequestDto statusId) {
		return productService.getProductsCountByStatus(statusId.getStatusId());
	}
	
	@GetMapping("/{id}")
	public ProductDto getProductById(@PathVariable("id") int id) {
		return conversionService.convert(productService.getProduct(id), ProductDto.class);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") int productId) {
		productService.deleteProduct(productId);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void updateProduct(
			@PathVariable("id") int productId, 
			@RequestPart("productRequestDto") ProductRequestDto productRequestDto, 
			@RequestPart("pictures") List<MultipartFile> pictures,
			@RequestPart("pictureTypeRequestDto") List<PictureTypeRequestDto> pictureTypeRequestDto) {
		
		List<ProductPicture> newPictures = conversionService.convertList(pictures, ProductPicture.class);
		
		productService.updateProduct(productRequestDto, productId, newPictures, pictureTypeRequestDto);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void addProduct(
			@RequestPart("productRequestDto") ProductRequestDto productRequestDto, 
			@RequestPart("pictures") List<MultipartFile> pictures) {
		
		Product product = conversionService.convert(productRequestDto, Product.class);
		product.setStatus(new Status(Status.NEW_ID));
		product.setCreatedAt(LocalDate.now());
		
		List<ProductPicture> productPictures = conversionService.convertList(pictures, ProductPicture.class);
		
		productService.saveProduct(product, productPictures, productRequestDto.getThumbPictureIndex(), productRequestDto.getProjectId());
	}
	
	@GetMapping(value = "/{id}/pictures")
	public List<ProductPictureDto> getPicturesByProductId(@PathVariable("id") int productId) {
		List<ProductPicture> pictures = pictureService.getPicturesByProductId(productId);
		return conversionService.convertList(pictures, ProductPictureDto.class);
	}
}
