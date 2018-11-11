package com.cat.config;

import com.cat.module.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by jxli on 2018/9/20.
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public String getCurrentAuditor() {
    Subject subject = SecurityUtils.getSubject();
    if (null == subject){
      return "sys";
    }
    User user = (User) subject.getPrincipal();
    if (user == null) {
      return "sys";
    }
    return user.getName();
  }
}
