package com.cat.module.dto;

/**
 * Created by jxli on 2018/9/21.
 */
public class RegisterDto {
  private String email;
  private String password;
  private String name;
  private String validateCode;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValidateCode() {
    return validateCode;
  }

  public void setValidateCode(String validateCode) {
    this.validateCode = validateCode;
  }

  @Override
  public String toString() {
    return "RegisterDto{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", validateCode='" + validateCode + '\'' +
        '}';
  }
}
