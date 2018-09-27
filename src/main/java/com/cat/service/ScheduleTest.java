package com.cat.service;

import com.cat.annotation.DistributeSchedule;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by jxli on 2018/9/27.
 */
@Service
public class ScheduleTest {

  @Scheduled(cron = "0 0/1 * * * ?")
  @DistributeSchedule
  public void autoAssign() {
    System.out.println( "定时任务执行啦"+ System.currentTimeMillis());
  }
}
