package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.PictureTypeRequestDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.vo.ProductVo;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private ProjectDaoImpl projectDao;
	
	@Autowired
	private PictureDaoImpl pictureDao;

	@Transactional
	public PageResult<ProductVo> getProducts(PageRequest pageRequest, ProductSearch search) {
		List<Product> products = productDao.getProducts(pageRequest, search);
		int totalCount = productDao.count(search);
		
		List<ProductVo> productsVo = new ArrayList<>();
		for (Product product : products) {
			int picturesCount = pictureDao.getPicturesCountByProductId(product.getId());
			
			ProductVo productVo = new ProductVo();
			productVo.setPicturesCount(picturesCount);
			productVo.setProduct(product);
			productsVo.add(productVo);
		}
		
		return new PageResult<>(productsVo, totalCount, pageRequest.getPageSize());
	}
	
	@Transactional
	public List<ProductVo> getProductsByProjectId(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException(Product.class, id);
		}
		
		List<Product> list = productDao.getProductsByProjectId(id);
		
		List<ProductVo> productsVo = new ArrayList<>();
		for (Product product : list) {
			int picturesCount = pictureDao.getPicturesCountByProductId(product.getId());
			
			ProductVo productVo = new ProductVo();
			productVo.setPicturesCount(picturesCount);
			productVo.setProduct(product);
			productsVo.add(productVo);
		}
		
		return productsVo;
	}
	
	@Transactional
	public void deleteProduct(int id) {
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException(Product.class, id);
		}
		
		List<ProductPicture> pictures = pictureDao.getPicturesByProductId(id);
		for (ProductPicture picture: pictures) {
			pictureDao.deletePicture(picture.getId());
		}
		
		productDao.deleteProduct(id);
	}

	@Transactional
	public void saveProduct(Product product, List<ProductPicture> pictures, int thumbIndex, int projectId) {
		// Set the thumb picture for the product
		ProductPicture thumbPicture = pictures.get(thumbIndex);
		thumbPicture.setProduct(product);
		product.setThumbPicture(thumbPicture);
		
		// Save the product
		Project project = projectDao.getProject(projectId);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, projectId);
		}
		product.setProject(project);
		productDao.saveProduct(product);
		
		// Save the pictures
		for (ProductPicture picture : pictures) {
			picture.setProduct(product);
			pictureDao.savePicture(picture);
		}
	}
	
	@Transactional
	public void updateProduct(ProductRequestDto productDto, int productId, List<ProductPicture> newPictures, 
			List<PictureTypeRequestDto> pictureTypeList) {
		
		Product existingProduct = productDao.getProduct(productId);
		if (existingProduct == null) {
			throw new EntityNotFoundException(Product.class, productId);
		}
		
		Project project = projectDao.getProject(productDto.getProjectId());
		if (project == null) {
			throw new EntityNotFoundException(Project.class, productDto.getProjectId());
		}
		existingProduct.setProject(project);
		existingProduct.setDescription(productDto.getDescription());
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setQuantity(productDto.getQuantity());
		existingProduct.setSerialNumber(productDto.getSerialNumber());
		existingProduct.setStatus(new Status(productDto.getStatusId()));

		List<ProductPicture> existingPictures = existingProduct.getPictures();
		
		int newPicturesIndex = 0;
		for (int i = 0; i < pictureTypeList.size(); i++) {
			if (pictureTypeList.get(i).getType().equals(PictureTypeRequestDto.TYPE_NEW)) {
				ProductPicture tempPicture = newPictures.get(newPicturesIndex++);
				tempPicture.setProduct(existingProduct);
				pictureDao.savePicture(tempPicture);
			}
		}
		
		existingPictures = existingProduct.getPictures();
		// Set the thumb picture for the product
		if (productDto.getThumbPictureIndex() >= 0) {
			ProductPicture thumbPicture = existingPictures.get(productDto.getThumbPictureIndex());
			thumbPicture.setProduct(existingProduct);
			existingProduct.setThumbPicture(thumbPicture);
		}
		
		for (int i = 0; i < pictureTypeList.size(); i++) {
			if (pictureTypeList.get(i).getType().equals(PictureTypeRequestDto.TYPE_DELETED)) {
				pictureDao.deletePicture(existingPictures.get(i).getId());
			} 
		}
	}

	@Transactional
	public Long getProductsCountByStatus(int statusId) {
		return productDao.getProductsCountByStatus(statusId);
	}

	@Transactional
	public Product getProduct(int id) {
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException(Product.class, id);
		}
		return product;
	}

//	@Transactional
//	public Long getProductsCountByProjectId(int projectId) {
//		return productDao.getProductsCountByProjectId(projectId);
//	}
}
