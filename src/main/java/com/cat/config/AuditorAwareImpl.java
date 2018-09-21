package com.cat.config;

import com.cat.module.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by jxli on 2018/9/20.
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public String getCurrentAuditor() {
    //TODO 待实现
    User current = null;
    if (current == null) {
      return "sys";
    }
    return current.getName();
  }
}
