package com.cat.config;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.cat.annotation.DataSource;

@Aspect
@Component
public class DataSourceAspect implements Ordered {

	@Around(value = "@annotation(com.cat.annotation.DataSource)")
	public Object changeDataSource(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		DataSource annotation = method.getAnnotation(DataSource.class);
		String oldDataSourceKey = null;
		if (annotation == null) {
			return point.proceed();
		} else {
			oldDataSourceKey = DynamicDataSource.getCurrentDataSourceKey();
			DynamicDataSource.setDataSourceKey(annotation.value());
		}
		
		try {
			return point.proceed();
		} finally {
			if (oldDataSourceKey != null) {
				DynamicDataSource.setDataSourceKey(oldDataSourceKey);
			} else {
				DynamicDataSource.reset();
			}
		}
	}

	@Override
	public int getOrder() {
		return 1;
	} 
}
