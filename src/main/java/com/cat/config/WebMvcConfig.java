package com.cat.config;

import com.cat.interceptor.RequestContextInterceptor;
import com.cat.interceptor.RoleInterceptor;
import com.mo9.nest.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private RequestContextInterceptor contextInterceptor;
    @Autowired
    private RoleInterceptor roleInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        registry.addInterceptor(contextInterceptor).addPathPatterns("/**");
        registry.addInterceptor(roleInterceptor).addPathPatterns("/**");

    }

}
