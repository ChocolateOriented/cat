package com.cat.config;

import com.cat.interceptor.CommonRequestContext;
import com.cat.module.entity.User;
import com.cat.repository.UserRepository;
import com.cat.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by jxli on 2018/9/20.
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {
  @Autowired
  UserRepository userRepository;

  @Override
  public String getCurrentAuditor() {
	CommonRequestContext instance = CommonRequestContext.getInstance();
	if (instance == null) {
		return "sys";
	}
	
    String userId = instance.getCurrentUserId();
    if (StringUtils.isBlank(userId)) {
      return "sys";
    }
    User current = userRepository.findOne(userId);
    return current.getName();
  }
}
