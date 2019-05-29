package com.cafe24.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfig {

	@Bean
	public DataSource basicDataSource() {
		BasicDataSource basicDataSeource = new BasicDataSource();
		basicDataSeource.setDriverClassName("org.mariadb.jdbc.Driver");
		basicDataSeource.setUrl("jdbc:mariadb://192.168.0.10:3307/webdb?allowMultiQueries=true&amp;sql_safe_updates=0");
		basicDataSeource.setUsername("webdb");
		basicDataSeource.setPassword("webdb");
		basicDataSeource.setInitialSize(10);
		basicDataSeource.setMaxActive(100);
		
		return basicDataSeource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
