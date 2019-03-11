package com.manos.prototype.config;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pastelstudios.convert.ConversionService;
import com.pastelstudios.convert.ConversionServiceImpl;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;
import com.pastelstudios.db.PagingAndSortingSupport;
import com.pastelstudios.db.SearchSupport;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.manos.prototype")
public class AppConfig {
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultiPartResolverMine();
		multipartResolver.setMaxUploadSize(12000000);
		return multipartResolver;
	}

	public static class CommonsMultiPartResolverMine extends CommonsMultipartResolver {
		@Override
		public boolean isMultipart(HttpServletRequest request) {
			final String header = request.getHeader("Content-Type");
			if(header == null){
				return false;
			}
			return header.contains("multipart/form-data");
		}
	}
	
	@Bean
	public ConversionService conversionService() {
		return new ConversionServiceImpl();
	}
	
	@Bean
	public GenericFinder genericFinder() {
		return new GenericFinder();
	}
	
	@Bean
	public GenericGateway genericGateway() {
		return new GenericGateway();
	}
	
	@Bean
	public PagingAndSortingSupport pagingAndSortingSupport() {
		return new PagingAndSortingSupport();
	}
	
	@Bean
	public SearchSupport searchSupport() {
		return new SearchSupport();
	}
}
