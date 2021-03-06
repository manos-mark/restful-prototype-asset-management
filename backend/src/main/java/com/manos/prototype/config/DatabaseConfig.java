package com.manos.prototype.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:persistence-mysql.properties")
public class DatabaseConfig {

	// set up a variable to hold data from properties file
		// automatic read classpath:persistence-mysql.properties
		@Autowired
		private Environment env;

		// define bean for Data Source
		@Bean
		public DataSource dataSource() {

			// create a connection pool (c3p0)
			ComboPooledDataSource dataSource = new ComboPooledDataSource();

			// set the jdbc driver class
			try {
				dataSource.setDriverClass(env.getProperty("jdbc.driver"));
			} catch (PropertyVetoException e) {
				throw new RuntimeException(e);
			}

			// set database connection props
			dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
			dataSource.setUser(env.getProperty("jdbc.user"));
			dataSource.setPassword(env.getProperty("jdbc.password"));

			// set connection pool props
			dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
			dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
			dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
			dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

			return dataSource;
		}
		
		// helper method, read environment property to convert to int
		private int getIntProperty(String propName) {
			return Integer.parseInt(env.getProperty(propName));
		}

		private Properties getHibernateProperties() {
			// set hibernate properties
			Properties props = new Properties();
			props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
			props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//			props.setProperty("hibernate.search.default.directory_provider", env.getProperty("hibernate.directory_provider"));
//			props.setProperty("hibernate.search.default.indexBase", env.getProperty("hibernate.indexBase"));
			return props;
		}

		// define bean for Session Factory
		@Bean
		public LocalSessionFactoryBean sessionFactory() {
			// create session factory
			LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
			// set the properties
			sessionFactory.setDataSource(dataSource());
			sessionFactory.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
			sessionFactory.setHibernateProperties(getHibernateProperties());
			return sessionFactory;
		}

}
