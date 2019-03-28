package com.manos.prototype.config;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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
//@ComponentScan(basePackages = "com.manos.prototype")
@ComponentScan(basePackages = {"com.manos.prototype"}, excludeFilters={
		  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=DatabaseConfig.class)})
public class AppConfigIntegrationTest {
	
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.H2);
		return builder.build();
	}
	
	private Properties getHibernateProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		props.setProperty("hibernate.connection.username", "sa");
		props.setProperty("hibernate.connection.password", "");
		props.setProperty("hibernate.hbm2ddl.auto", "create");
		return props;				
	}
	
	// define bean for Session Factory
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// create session factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		// set the properties
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.manos.prototype.entity");
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}
	
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
