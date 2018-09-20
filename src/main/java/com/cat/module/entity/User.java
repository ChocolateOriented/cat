package com.cat.module.entity;

import com.cat.module.enums.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {

  private Long organizationId;
  @Column(nullable = false)
  private String loginName;
  @Column(nullable = false)
  private String password;
  private String name;
  @Column(nullable = false)
  private UserStatus status;
  private String dunningCycle; //示例 10100 代表Q0,Q2

  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }


  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
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

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public String getDunningCycle() {
    return dunningCycle;
  }

  public void setDunningCycle(String dunningCycle) {
    this.dunningCycle = dunningCycle;
  }

  @Override
  public String toString() {
    return "User{" +
        "organizationId=" + organizationId +
        ", loginName='" + loginName + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", status=" + status +
        ", dunningCycle='" + dunningCycle + '\'' +
        "} " + super.toString();
  }
}
