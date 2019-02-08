package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
//	@Override
//	public List<Product> getProducts() {
//		Session currentSession = sessionFactory.getCurrentSession();
//		Query<Product> theQuery = currentSession
//				.createQuery("from Product", Product.class);
//		return theQuery.getResultList();
//	}

	@Override
	public Product getProduct(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Product> theQuery = currentSession
				.createQuery("from Product p where p.id = :id", Product.class);
		theQuery.setParameter("id", id);
		return theQuery.getSingleResult();
	}
	
	@Override
	public List<Product> getProductsByProjectId(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Product> theQuery = currentSession
				.createQuery("from Product p where p.project.id = :id", Product.class);
		theQuery.setParameter("id", id);
		return theQuery.getResultList();
	}

	@Override
	public void deleteProduct(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Product> theQuery = currentSession
				.createQuery("from Product p where p.id = :id", Product.class);
		theQuery.setParameter("id", id);
		Product project = theQuery.getSingleResult();
		currentSession.delete(project);	
	}

	@Override
	public void saveProduct(Product product) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(product);
	}

}
