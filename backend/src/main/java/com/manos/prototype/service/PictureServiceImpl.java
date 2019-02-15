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
	public void savePicture(ProductPicture picture) {
		try {
			pictureDao.savePicture(picture);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}
	
	@Transactional
	public Long getPicturesCountByProductId(int productId) {
		return pictureDao.getPicturesCountByProductId(productId);
	}
}
