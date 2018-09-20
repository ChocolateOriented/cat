package com.cat.config;

import javax.annotation.PostConstruct;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.cat.util.HttpClientUtil;

@Configuration
@ConfigurationProperties("httpclient.pool")
public class HttpClientConfig {

	private int maxTotal;

	private int defaultMaxPerRoute;

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	@PostConstruct
	public void init() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxTotal);
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		HttpClientUtil.init(connectionManager);
	}
}
