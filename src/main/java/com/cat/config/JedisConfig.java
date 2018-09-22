package com.cat.config;

import com.mo9.nest.client.redis.RedisHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class JedisConfig {

	private boolean ssl;

	private int timeout;

	private int maxTotal;

	private int maxWait;

	private int maxIdle;

	private int minIdle;

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	@Bean(name = "commonRedisPool")
	public JedisPool commonRedisPool(@Value("${redis.default.host}") String host,
			@Value("${redis.default.port}") int port,
			@Value("${redis.default.password}") String password,
			@Value("${redis.default.database}") int database ) {

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMinIdle(minIdle);
		JedisPool pool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database, ssl);
		return pool;
	}


	@Bean
	public RedisConnectionFactory nestRedisConnectionFactory(
			@Value("${redis.nest.host}") String host,
			@Value("${redis.nest.port}") int port,
			@Value("${redis.nest.password}") String password,
			@Value("${redis.nest.database}") int database ) {

			JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
			jedisPoolConfig.setMaxIdle(maxIdle);
			jedisPoolConfig.setMaxWaitMillis(maxWait);
			jedisPoolConfig.setMaxTotal(maxTotal);
			jedisPoolConfig.setMinIdle(minIdle);
			JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
			jedisConnectionFactory.setHostName(host);
			if(!password.isEmpty()){
				jedisConnectionFactory.setPassword(password);
			}
			jedisConnectionFactory.setPort(port);
			jedisConnectionFactory.setDatabase(database);
			return jedisConnectionFactory;
		}


	@Bean(name = "nestRedisHolder")
	public RedisHolder redisHolder(@Autowired RedisConnectionFactory nestRedisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(nestRedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(template.getKeySerializer());
		template.setHashValueSerializer(template.getValueSerializer());

		return new RedisHolder(template);
	}
}
