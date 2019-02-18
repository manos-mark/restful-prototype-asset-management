package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Product;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class PictureServiceImpl {

	@Autowired
	private PictureDaoImpl pictureDao;
	
	@Autowired
	private ProductDaoImpl productDao;
	
	@Transactional
	public List<ProductPicture> getPicturesByProductId(int productId) {
		Product product = productDao.getProduct(productId);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + productId);
		}
		return pictureDao.getPicturesByProductId(productId);
	}
	
	@Transactional
	public ProductPicture getPicture(int id) {
		ProductPicture picture = pictureDao.getPicture(id);
		if (picture == null) {
			throw new EntityNotFoundException("Picture id not found - " + id);
		}
		return picture;
	}
	
	@Transactional
	public ProductPicture getThumbPictureByProductId(int productId) {
		Product product = productDao.getProduct(productId);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + productId);
		}
		return pictureDao.getThumbPictureByProductId(productId);
	}
	
	@Transactional
	public void savePicture(ProductPicture pic) {
		if (pic.getPicture() == null) {
			throw new EntityNotFoundException("Picture cannot be null");
		}
		else if (pic.getProduct() == null) {
			throw new EntityNotFoundException("Product cannot be null");
		}
		else {
			// if id is not 0 then update
			if (pic.getId() != 0) {
				pictureDao.updatePicture(pic);			
			} 
			// if id is 0 then add new
			else {
				pictureDao.savePicture(pic);			
			}
		}
	}
	
	@Transactional
	public Long getPicturesCountByProductId(int productId) {
		return pictureDao.getPicturesCountByProductId(productId);
	}
}
