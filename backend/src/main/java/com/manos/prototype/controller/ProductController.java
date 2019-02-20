package com.manos.prototype.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.controller.params.ProductFilterParams;
import com.manos.prototype.controller.params.ProductOrderAndPageParams;
import com.manos.prototype.dto.PageResultDto;
import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProductPictureDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.service.PictureServiceImpl;
import com.manos.prototype.service.ProductServiceImpl;
import com.manos.prototype.service.ProjectServiceImpl;
import com.pastelstudios.convert.ConversionService;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ProjectServiceImpl projectService;

	@Autowired
	private PictureServiceImpl pictureService;

	@Autowired
	private ConversionService conversionService;

	@GetMapping
	public PageResultDto<ProductDto> getProductsPaginated(@Valid ProductOrderAndPageParams pageParams,
			@Valid ProductFilterParams filterParams) {

		PageRequest pageRequest = conversionService.convert(pageParams, PageRequest.class);
		ProductSearch search = conversionService.convert(filterParams, ProductSearch.class);

		PageResult<Product> pageResult = productService.getProducts(pageRequest, search);

		List<ProductDto> productsDto = conversionService.convertList(pageResult.getEntities(), ProductDto.class);
		
		for (ProductDto product : productsDto) {
			Project project = projectService.getProject(product.getProjectId());
			product.setProjectName(project.getProjectName());
			
			Long picturesCount = pictureService.getPicturesCountByProductId(product.getId());
			product.setPicturesCount(picturesCount);
		}
		
		PageResultDto<ProductDto> pageResultDto = new PageResultDto<>();
		pageResultDto.setItems(productsDto);
		pageResultDto.setTotalCount(pageResult.getTotalCount());
		return pageResultDto;
	}

	@PostMapping("/count")
	public Long getProductsCountByStatus(@RequestBody StatusRequestDto statusId) {
		return productService.getProductsCountByStatus(statusId.getStatusId());
	}

	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int productId) {
		productService.deleteProduct(productId);
		return "Deleted user with id - " + productId;
	}

	@PutMapping("/{id}")
	public void updateProduct(@PathVariable("id") int productId, @RequestBody ProductRequestDto productRequestDto) {
		Product product = conversionService.convert(productRequestDto, Product.class);
		product.setId(productId);

//		// check if project exists
//		int projectId = product.getProject().getId();
//		Project project = projectService.getProject(projectId);
//
//		if (project == null) {
//			throw new EntityNotFoundException("Project id not found - " + projectId);
//		}

		productService.saveProduct(product);
	}

	@PostMapping
	public int addProduct(@RequestBody ProductRequestDto productRequestDto) {
		Product product = conversionService.convert(productRequestDto, Product.class);
		product.setId(0);
		productService.saveProduct(product);
		return product.getId();
	}
	
	@GetMapping(value = "/{id}/pictures", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_GIF_VALUE })
	public List<ProductPictureDto> getPicturesByProductId(@PathVariable("id") int productId) {
		List<ProductPicture> pictures = pictureService.getPicturesByProductId(productId);
		return conversionService.convertList(pictures, ProductPictureDto.class);
	}
//
//	@GetMapping(value = "/{id}/thumb-picture", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
//			MediaType.IMAGE_GIF_VALUE })
//	public byte[] getThumbPictureByProductId(@PathVariable("id") int productId) {
//		ProductPicture picture = pictureService.getThumbPictureByProductId(productId);
//		return picture.getPicture()
//	}
//
//	@GetMapping(value = "/{id}/pictures-count")
//	public Long getPicturesCountByProductId(@PathVariable("id") int productId) {
//		return pictureService.getPicturesCountByProductId(productId);
//	}
//
//	@PostMapping("/{id}/pictures")
//	public void addPicture(@PathVariable("id") int productId, @RequestBody ProductPictureRequestDto pictureDto) {
//		ProductPicture picture = conversionService.convert(pictureDto, ProductPicture.class);
//		picture.setId(0);
//		pictureService.savePicture(picture);
//	}
//
//	@PutMapping("/{id}/pictures/{pictureId}")
//	public void updatePicture(@PathVariable("id") int productId, @PathVariable("pictureId") int pictureId,
//			@RequestBody ProductPictureRequestDto pictureDto) {
//		ProductPicture picture = conversionService.convert(pictureDto, ProductPicture.class);
//		picture.setId(pictureId);
//		pictureService.savePicture(picture);
//	}
}
