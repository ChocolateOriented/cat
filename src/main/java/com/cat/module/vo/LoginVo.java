package com.cat.module.vo;

import com.cat.module.dto.RoleDto;
import com.cat.module.entity.Role;

/**
 * Created by jxli on 2018/9/25.
 */
public class LoginVo {

  private String userId;
  private String name;
  private RoleDto role;
  private String organizationName;
  private String accessToken;

  public LoginVo() {
  }

  public LoginVo(String userId, String name, String accessToken) {
    this.userId = userId;
    this.name = name;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RoleDto getRole() {
    return role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

  public void setRole(Role role) {
    if (role == null||!role.getEnabled()){
      return;
    }
    this.role = new RoleDto(role);
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }
}
