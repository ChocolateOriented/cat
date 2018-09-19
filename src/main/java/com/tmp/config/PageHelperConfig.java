package com.tmp.config;

import com.github.pagehelper.PageHelper;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageHelperConfig {

  //配置mybatis的分页插件pageHelper
  @Bean
  public PageHelper pageHelper() {
    PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    properties.setProperty("dialect", "mysql");    //配置mysql数据库的方言
    pageHelper.setProperties(properties);
    return pageHelper;
  }

}
