/*
 * This code is sample code, provided as-is, and we make NO 
 * warranties as to its correctness or suitability for any purpose.
 * 
 * We hope that it's useful to you. Enjoy. 
 * Copyright LearningPatterns Inc.
 */
 
package com.javatunes.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:jdbc.properties") // Annotation to enable access to the jdbc.properties file
// DONE: Enable Automatic JPA Repository creation, using classes in com.javatunes.persistence, and set the entity manager ref appropriately for our configuration. 
@EnableJpaRepositories(basePackages = {"com.javatunes.persistence"}, entityManagerFactoryRef="entityManagerFactory")
public class SpringRepositoryConfig {

	// Inject the Environment
	@Autowired
	private Environment env; 
	
	   @Bean
	   public static PropertySourcesPlaceholderConfigurer
	     propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	   }

	@Bean DataSource javatunesDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setUrl(env.getProperty("javatunes.url"));  
		ds.setDriverClassName(env.getProperty("javatunes.driverClassName"));
		ds.setUsername(env.getProperty("javatunes.dbUserName"));
		ds.setPassword(env.getProperty("javatunes.password"));


		return ds;
	}

	@Bean
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		va.setShowSql(true);
		va.setGenerateDdl(false);
		return va;
	}
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean javatunesEmf() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		// Get the "persistenceUnitName" property and add it to the EM
		em.setPersistenceUnitName(env.getProperty("javatunes.persistenceUnitName")); 

		em.setDataSource(javatunesDataSource());
		em.setPackagesToScan("com.javatunes.*");
		em.setJpaVendorAdapter(vendorAdapter());
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(javatunesEmf().getObject());
		return transactionManager;
	} 	
	
	private Properties additionalProperties() {
	      Properties properties = new Properties();
	      properties.setProperty("hibernate.dialect", env.getProperty("javatunes.dialect")); // I would have the student edit this in the properties file
	      return properties;
	   }


}