package com.cat.module.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxli on 2018/10/16.
 */
public class ResetPasswordDto {
  @NotBlank(message = "无效邮箱")
  @Email(message = "无效邮箱")
  private String email;
  @NotBlank(message = "密码不能为空")
  private String password;
  @NotBlank(message = "无效令牌")
  private String token;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "ResetPasswordDto{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", token='" + token + '\'' +
        '}';
  }
}
