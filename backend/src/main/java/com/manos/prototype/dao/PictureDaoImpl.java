package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.ProductPicture;

@Repository
public class PictureDaoImpl {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<ProductPicture> getPicturesByProductId(int productId) {
		Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from ProductPicture pic ")
                	.append("join fetch pic.product pr ")
                	.append("where pr.id = :productId ");
        Query<ProductPicture> theQuery = currentSession
                .createQuery(queryBuilder.toString(), ProductPicture.class);
        theQuery.setParameter("productId", productId);
        
        return theQuery.getResultList();
	}
	
	public ProductPicture getPicture(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from ProductPicture pic ")
                	.append("join fetch pic.product ")
                	.append("where pic.id = :id ");
        Query<ProductPicture> theQuery = currentSession
                .createQuery(queryBuilder.toString(), ProductPicture.class);
        theQuery.setParameter("id", id);
        
        return theQuery.getSingleResult();
	}
	
	public void savePicture(ProductPicture picture) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(picture);
	}
	
	public void deletePicture(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<ProductPicture> theQuery = currentSession
				.createQuery("from ProductPicture p where p.id = :id", ProductPicture.class);
		theQuery.setParameter("id", id);
		
		ProductPicture project = theQuery.getSingleResult();
		currentSession.delete(project);	
	}
	
	public Long getPicturesCountByProductId(int productId) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(pic.id) from ProductPicture pic ")
                	.append("join pic.product pr ")
                	.append("where pr.id = :productId ");
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("productId", productId);
		return theQuery.getSingleResult();
	}

	public ProductPicture getThumbPictureByProductId(int productId) {
		Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from ProductPicture pic ")
                	.append("join fetch pic.product pr ")
                	.append("where pr.id = :productId ")
        			.append("and pic.thumb = true");
        Query<ProductPicture> theQuery = currentSession
                .createQuery(queryBuilder.toString(), ProductPicture.class);
        theQuery.setParameter("productId", productId);
        
        return theQuery.getSingleResult();
	}

	public void updatePicture(ProductPicture pic) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(pic);
	}
}
















