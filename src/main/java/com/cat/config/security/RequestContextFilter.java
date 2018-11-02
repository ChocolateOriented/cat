package com.cat.config.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;

@Order(0)
@WebFilter(filterName = "requestContextFilter", urlPatterns = "/*")
public class RequestContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		CommonRequestContext.newInstance(httpReq);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	
}
