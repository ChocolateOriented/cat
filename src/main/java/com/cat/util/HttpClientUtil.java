package com.cat.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClientUtil {

	private static CloseableHttpClient client;

	public static void init(PoolingHttpClientConnectionManager connectionManager) {
		client = HttpClients.custom().setConnectionManager(connectionManager).build();
	}

	public static String get(String url) {
		HttpGet get = new HttpGet(url);
		String result = null;
		try {
			HttpResponse httpResponse = client.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static String get(String url, Map<String, String> params) {
		StringBuilder requestUrl = new StringBuilder(url);
		for (Entry<String, String> entry : params.entrySet()) {
			requestUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return get(requestUrl.toString());
	}

	public static String postJson(String url, String jsonStr) {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-type", "application/json; charset=utf-8");
        String result = null;
		try {
			HttpEntity reqEntity = new StringEntity(jsonStr.toString());
			post.setEntity(reqEntity);
			
			HttpResponse httpResponse = client.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static String postJson(String url, Object jsonObj) {
		return postJson(url, JSON.toJSONString(jsonObj));
	}

	public static String postForm(String url, Map<String, Object> params) {
		HttpPost post = new HttpPost(url);
        String result = null;
        
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        
		try {
			HttpEntity reqEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
			post.setEntity(reqEntity);
			
			HttpResponse httpResponse = client.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
