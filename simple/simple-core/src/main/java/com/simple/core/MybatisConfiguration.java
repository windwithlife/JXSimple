package com.simple.core;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.mybatis.spring.SqlSessionTemplate;

@Configuration
public class MybatisConfiguration {

	@Value("#{dataSource}")
	private DataSource dataSource;

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(this.dataSource);
		sessionFactory.setTypeAliasesPackage("com.simple");
		return sessionFactory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() {
		SqlSessionTemplate bean = null;
		try {
			bean = new SqlSessionTemplate(sqlSessionFactory().getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
		
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer bean = new MapperScannerConfigurer();
		bean.setBasePackage("com.simple");
		return bean;
		
	}
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager bean = new DataSourceTransactionManager();
		bean.setDataSource(this.dataSource);
		return bean;
		
	}

}
