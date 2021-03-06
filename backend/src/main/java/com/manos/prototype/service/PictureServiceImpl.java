package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.finder.PictureFinder;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;

@Service
public class PictureServiceImpl {

	@Autowired
	private PictureFinder pictureDao;
	
	@Autowired
	private GenericFinder finder;
	
	@Autowired
	private GenericGateway gateway;
	
	@Transactional
	public List<ProductPicture> getPicturesByProductId(int productId) {
		Product product = finder.findById(Product.class, productId);
		if (product == null) {
			throw new EntityNotFoundException(Product.class, productId);
		}
		return pictureDao.getPicturesByProductId(productId);
	}
	
	@Transactional
	public ProductPicture getPicture(int id) {
		ProductPicture picture = finder.findById(ProductPicture.class, id);
		if (picture == null) {
			throw new EntityNotFoundException(ProductPicture.class, id);
		}
		return picture;
	}
	
	@Transactional
	public void savePicture(ProductPicture pic) {
		if (pic.getPicture() == null) {
			throw new EntityNotFoundException(ProductPicture.class);
		}
		else if (pic.getProduct() == null) {
			throw new EntityNotFoundException(ProductPicture.class);
		}
		else {
			gateway.save(ProductPicture.class);			
		}
	}
}
