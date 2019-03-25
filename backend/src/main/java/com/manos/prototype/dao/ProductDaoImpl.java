package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
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
	
	public List<Product> getProducts(PageRequest pageRequest, ProductSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryByilder = new StringBuilder();
		queryByilder.append("from Product product ")
					.append(" join fetch product.project project ")
					.append(" join fetch product.thumbPicture thumbPicture ")
					.append(" join fetch product.status productStatus ");
		String queryString = searchSupport.addSearchConstraints(queryByilder.toString(), search);
		queryString = pagingSupport.applySorting(queryString, pageRequest);
		
		return pagingSupport
				.applyPaging(currentSession.createQuery(queryString, Product.class), pageRequest)
				.setProperties(search)
				.getResultList();
	}
	
	public List<Product> getProductsByProjectId(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Product p ")
					.append("join fetch p.status ")
					.append("join fetch p.project ")
					.append("where p.project.id = :id");
		Query<Product> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Product.class);
		theQuery.setParameter("id", id);
		
		return theQuery.getResultList();
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

	public int count(ProductSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(product.id) from Product product ")
					.append(" join product.project project ")
					.append(" join product.status productStatus ");
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		
		Long count = currentSession.createQuery(queryString, Long.class)
							.setProperties(search)
							.getSingleResult();
		return count == null ? 0 : count.intValue();
	}

	public List<Product> search(String text, String field) {
		Session currentSession = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(currentSession);
		// Using an Hibernate Session to rebuild an index
//		try {
//			fullTextSession.createIndexer().startAndWait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		QueryBuilder qb = fullTextSession
							.getSearchFactory()
							.buildQueryBuilder()
							.forEntity(Product.class)
							.overridesForField(field, "my_analyzer_without_ngrams")
							.get();
		
		org.apache.lucene.search.Query lucenceQuery 
			= qb.keyword()
				.onFields(field)
				.matching(text)
				.createQuery();
		
		return fullTextSession.createFullTextQuery(lucenceQuery, Product.class)
					.getResultList();
	}

	public Product getProductByName(String productName) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Product product ")
					.append("where product.productName = :productName");
		
		Query<Product> query = currentSession.createQuery(queryBuilder.toString(), Product.class);
		query.setParameter("productName", productName);
		
		return query.uniqueResult();
	}

	public Product getProductBySerialNumber(String serialNumber) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Product product ")
					.append("where product.serialNumber = :serialNumber");
		
		Query<Product> query = currentSession.createQuery(queryBuilder.toString(), Product.class);
		query.setParameter("serialNumber", serialNumber);
		
		return query.uniqueResult();
	}
}
