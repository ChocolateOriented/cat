package com.cat.config;

import com.cat.util.DateUtils;
import com.cat.util.RedisUtil;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
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

  @Before(value = "@annotation(com.cat.annotation.DistributeSchedule)")
  public void changeDataSource(JoinPoint point) throws Throwable {
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

    boolean lockSuccess = RedisUtil.tryLock(functionName+currentMinute,ip ,5*1000,24*60*60*1000);
    //TODO
  }


  @Override
  public int getOrder() {
    return 1;
  }
}
