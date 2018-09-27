package com.cat.config;

import com.cat.interceptor.RequestInfoFetcher;
import com.mo9.nest.auth.AuthInterceptor;
import com.mo9.nest.client.AuthClient;
import com.mo9.nest.client.redis.RedisHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCenterConfig {

    @Autowired
    private RedisHolder nestRedisHolder;
    private RequestInfoFetcher requestInfoFetcher = new RequestInfoFetcher();

    /**
     * 认证客户端, 封装了用户中心常规认证请求, 同时也是AuthInterceptor的依赖
     * @param baseUrl       用户中心基础URL, 例如: http://127.0.0.1:8081/nestApi/
     * @param key           key
     * @param secret        secret
     * @param systemCode    系统代号(用户中心分配)
     * @param systemGroup   系统分组(用户中心分配)
     * @param loginMode     登录模式(COMMON/ALONE/GROUP, 分别代表公共模式/单一模式和分组模式)
     * @return
     */
    @Bean
    public AuthClient authenticationClient(@Value("${nest.url}") String baseUrl,
                                           @Value("${nest.key}") String key,
                                           @Value("${nest.secret}") String secret,
                                           @Value("${nest.code}") String systemCode,
                                           @Value("${nest.group}") String systemGroup,
                                           @Value("${nest.mode}") String loginMode) {
        AuthClient client = new AuthClient(baseUrl, key, secret, systemCode, systemGroup, loginMode);
        //这里的配置后面会说到
        client.setRequestInfoFetcher(requestInfoFetcher);
        return client;
    }

    /**
     * 认证拦截器
     * @param excludeUrls 排除拦截的URL, 支持pattern
     * @return
     */
    @Bean
    public AuthInterceptor authenticationInterceptor(@Value("${nest.exclude.urls}") String[] excludeUrls) {
        AuthInterceptor authInterceptor = new AuthInterceptor(nestRedisHolder);
        //这里的配置后面会说到
        authInterceptor.setRequestInfoFetcher(requestInfoFetcher);
        if (excludeUrls != null) {
            authInterceptor.setExcludeUrls(excludeUrls);
        }
        return authInterceptor;
    }

}