package com.cat.config.security;

import com.mo9.nest.client.AuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCenterConfig {

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
        client.setRequestInfoFetcher(requestInfoFetcher);
        return client;
    }


}
