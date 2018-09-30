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
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Created by jxli on 2018/9/27.
 */
@Aspect
@Component
public class ClustersScheduleAspect implements Ordered {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Around(value = "@annotation(com.cat.annotation.ClustersSchedule)")
  public void changeDataSource(ProceedingJoinPoint point) throws Throwable {
    String localAddress = this.getLocalAddress();
    String taskKey = this.getTaskKey(point);

    boolean lockSuccess = RedisUtil.tryLock(taskKey ,localAddress ,5*1000,24*60*60*1000);
    if (!lockSuccess){
      logger.debug(localAddress+"未能执行任务"+taskKey);
      return;
    }
    logger.info(localAddress+"成功执行任务"+taskKey);
    point.proceed();
  }

  /**
   * @Description 待执行方法名+当前时间(精确至分钟)
   * @param point
   * @return java.lang.String
   */
  private String getTaskKey(ProceedingJoinPoint point) {
    String currentMinute = DateUtils.getDate("yyyy-MM-dd,HH:mm");
    String functionName = point.getSignature().getDeclaringTypeName()+ point.getSignature()
        .getName();
    return functionName+currentMinute;
  }

  private String getLocalAddress(){
    try {
      InetAddress localHost = Inet4Address.getLocalHost();
      return localHost.getHostAddress();
    } catch (UnknownHostException e) {
      logger.error(e.getMessage(),e);
    }
    return "";
  }


  @Override
  public int getOrder() {
    return 1;
  }
}
