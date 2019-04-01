package com.capp.config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;

/*
 * Here configuration of Application layer is done (Middleware service component)
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.capp.dao", "com.capp.service"})
public class SpringRootConfig {
 //TODO: add beans for services , DAO, data sources, email sender etc
	
	@Bean
	public BasicDataSource getDataSource() {
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/capp_db");
		ds.setUsername("root");
		ds.setPassword("sqlpass1");
		ds.setMaxTotal(2); // max 2 connections
		ds.setInitialSize(0);  // initially 1 connection and it can extend to 2 as max limit is 2.
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("SELECT 1");
		ds.setDefaultAutoCommit(true);
		return ds;
		
	}
}
