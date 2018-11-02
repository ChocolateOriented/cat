package com.cat.config.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by jxli on 2018/10/31.
 */
public class StatelessToken implements AuthenticationToken {

  private String userId;

  private String token;

  public StatelessToken(String userId, String token){
    this.userId = userId;
    this.token = token;
  }


  @Override
  public Object getPrincipal() {
    return userId;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

}
