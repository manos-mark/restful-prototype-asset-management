package com.manos.prototype.finder;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.ProductPicture;
import com.pastelstudios.db.AbstractFinder;

@Repository
public class PictureFinder extends AbstractFinder {
	
	public List<ProductPicture> getPicturesByProductId(int productId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from ProductPicture pic ")
                	.append("join fetch pic.product pr ")
                	.append("where pr.id = :productId ");
        Query<ProductPicture> theQuery = createQuery(queryBuilder.toString(), ProductPicture.class);
        theQuery.setParameter("productId", productId);
        
        return theQuery.getResultList();
	}
	
	public int getPicturesCountByProductId(int productId) {
		StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(pic.id) from ProductPicture pic ")
                	.append("join pic.product pr ")
                	.append("where pr.id = :productId ");
		Query<Long> theQuery = createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("productId", productId);
		
		Long count = theQuery.getSingleResult();
		return count == null ? 0 : count.intValue();
	}
}
















