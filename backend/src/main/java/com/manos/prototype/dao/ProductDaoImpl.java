package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Product;
import com.manos.prototype.search.ProductSearch;
import com.pastelstudios.db.PagingAndSortingSupport;
import com.pastelstudios.db.SearchSupport;
import com.pastelstudios.paging.PageRequest;

@Repository
public class ProductDaoImpl {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private SearchSupport searchSupport;
	
	@Autowired
	private PagingAndSortingSupport pagingSupport;
	
	public Product getProduct(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Product p "
				+ "join fetch p.status "
				+ "join fetch p.project "
				+ "where p.id = :id");
		Query<Product> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Product.class);
		theQuery.setParameter("id", id);
		
		return theQuery.getSingleResult();
	}
	
	public List<Product> getProductsByProjectId(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Product p "
				+ "join fetch p.status "
				+ "join fetch p.project "
				+ "where p.project.id = :id");
		Query<Product> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Product.class);
		theQuery.setParameter("id", id);
		
		return theQuery.getResultList();
	}

	public void deleteProduct(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Product> theQuery = currentSession
				.createQuery("from Product p where p.id = :id", Product.class);
		theQuery.setParameter("id", id);
		
		Product project = theQuery.getSingleResult();
		currentSession.delete(project);	
	}

	public void saveProduct(Product product) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(product);
	}
	
	public void updateProduct(Product product) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(product);
	}

	public Long getProductsCountByStatus(int statusId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Product p ")
					.append("inner join p.status s ")
					.append("where s.id = :statusId");
		
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("statusId", statusId);
		
		return theQuery.getSingleResult();
	}

	public Long count(ProductSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Product p ")
					.append("join p.status pStatus ");
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		
		Query<Long> theQuery = currentSession.createQuery(queryString, Long.class);
		theQuery.setParameter("statusId", search.getStatusId());
		
		return theQuery.getSingleResult();
	}

	public List<Product> getProducts(PageRequest pageRequest, ProductSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryByilder = new StringBuilder();
		queryByilder.append("from Product p ")
					.append("join fetch p.status pStatus");
		String queryString = searchSupport.addSearchConstraints(queryByilder.toString(), search);
		queryString = pagingSupport.applySorting(queryString, pageRequest);
		
		return pagingSupport
				.applyPaging(currentSession.createQuery(queryString, Product.class), pageRequest)
				.setProperties(search)
				.getResultList();
	}

	public Long getProductsCountByProjectId(int projectId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Product p "
				+ "inner join p.project pr "
				+ "where pr.id = :projectId");
		
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("projectId", projectId);
		
		return theQuery.getSingleResult();
	}

	public Long countAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Product p ");
		
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		
		return theQuery.getSingleResult();
	}

}
