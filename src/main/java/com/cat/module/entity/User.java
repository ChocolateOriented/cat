package com.cat.module.entity;


public class User extends BaseEntity {

  private Long organizationId;
  private String loginName;
  private String password;
  private String name;
  private Long status;
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


  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }


  public String getDunningCycle() {
    return dunningCycle;
  }

  public void setDunningCycle(String dunningCycle) {
    this.dunningCycle = dunningCycle;
  }

}
