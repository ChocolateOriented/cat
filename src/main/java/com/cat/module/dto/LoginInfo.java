package com.cat.module.dto;

/**
 * Created by jxli on 2018/9/25.
 */
public class LoginInfo {
  private String userId;
  private String accessToken;

  public LoginInfo(String userId, String accessToken) {
    this.userId = userId;
    this.accessToken = accessToken;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
