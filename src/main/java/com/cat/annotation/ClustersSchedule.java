package com.cat.annotation;

import com.cat.config.DynamicDataSource;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Description 集群任务调度, 只支持执行间隔在一分钟之上的定时任务
 * @author jxli
 * @version 2018/9/27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClustersSchedule {

	String value() default DynamicDataSource.DEFAULT_DATASOURCE;
}
