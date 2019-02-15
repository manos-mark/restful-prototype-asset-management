package com.manos.prototype.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.pastelstudios.db.PagingAndSortingSupport;
import com.pastelstudios.db.SearchSupport;

@Configuration
@ComponentScan(basePackages = "com.manos.prototype.dao")
public class AppConfigUnitTest {
	
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
//		props.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
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
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
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