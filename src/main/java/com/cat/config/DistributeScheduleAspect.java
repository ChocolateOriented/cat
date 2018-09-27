package com.cat.config;

import com.cat.util.DateUtils;
import com.cat.util.RedisUtil;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Created by jxli on 2018/9/27.
 */
@Aspect
@Component
public class DistributeScheduleAspect implements Ordered {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Value("server.prot")
  private String port;

  @Around(value = "@annotation(com.cat.annotation.DistributeSchedule)")
  public void changeDataSource(ProceedingJoinPoint point) throws Throwable {
    String currentMinute = DateUtils.getDate("yyyy-MM-dd,HH:mm");
    String functionName = point.getSignature().getDeclaringTypeName()+ point.getSignature()
        .getName();
    String ip = null;
    try {
      InetAddress localHost = Inet4Address.getLocalHost();
      ip = localHost.getHostAddress()+":"+port;
    } catch (UnknownHostException e) {
      logger.error(e.getMessage(),e);
    }
    String taskKey = functionName+currentMinute;
    boolean lockSuccess = RedisUtil.tryLock(taskKey ,ip ,5*1000,24*60*60*1000);
    if (lockSuccess){
      logger.debug(ip+"成功执行任务"+taskKey);
      point.proceed();
    }
    logger.debug(ip+"未能执行任务"+taskKey);
    return;
  }


  @Override
  public int getOrder() {
    return 1;
  }
}
