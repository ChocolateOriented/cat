package com.cat.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	public static final String DEFAULT_DATASOURCE = "default";

	public static final String RISK_DATASOURCE = "risk";

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  

	@Override
	protected Object determineCurrentLookupKey() {
		return contextHolder.get();
	}

	public static void setDataSourceKey(String currentLookupKey) {  
        contextHolder.set(currentLookupKey);  
    }

	public static String getCurrentDataSourceKey() {
		return contextHolder.get();
	}

	public static void reset() {  
        contextHolder.remove();
    }
}
