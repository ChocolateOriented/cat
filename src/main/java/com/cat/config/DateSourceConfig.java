package com.cat.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DateSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.default")
	public DataSourceProperties defauleDataSourceProperties() {
		DataSourceProperties defaultProperties = new DataSourceProperties();
		defaultProperties.setInitialize(false);
		return defaultProperties;
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource defaultDataSource() {
		return defauleDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.risk")
	public DataSourceProperties riskDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource riskDataSource() {
		return riskDataSourceProperties().initializeDataSourceBuilder().build();
	}
	@Bean
	@ConfigurationProperties("spring.datasource.raptor")
	public DataSourceProperties raptorDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource raptorDataSource() {
		return raptorDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	public DataSource dynamicDataSource(DataSource defaultDataSource, DataSource riskDataSource, DataSource raptorDataSource) {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DynamicDataSource.DEFAULT_DATASOURCE, defaultDataSource);
		targetDataSources.put(DynamicDataSource.RISK_DATASOURCE, riskDataSource);
		targetDataSources.put(DynamicDataSource.RAPTOR_DATASOURCE, raptorDataSource);
		
		dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		return dynamicDataSource;
	}
}
