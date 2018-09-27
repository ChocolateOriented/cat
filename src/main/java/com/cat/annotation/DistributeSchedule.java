package com.cat.annotation;

import com.cat.config.DynamicDataSource;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeSchedule {

	String value() default DynamicDataSource.DEFAULT_DATASOURCE;
}
