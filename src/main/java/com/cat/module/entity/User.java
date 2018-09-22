package com.cat.module.entity;

import com.cat.module.enums.UserStatus;
import com.cat.module.enums.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

  private Long organizationId;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  private String name;
  @Column(nullable = false)
  private UserStatus status;
  private String collectCycle; //示例 10100 代表Q0,Q2
  private Role role;//角色

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
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

  public String getCollectCycle() {
    return collectCycle;
  }

  public void setCollectCycle(String collectCycle) {
    this.collectCycle = collectCycle;
  }

  @Override
  public String toString() {
    return "User{" +
        "organizationId=" + organizationId +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", status=" + status +
        ", collectCycle='" + collectCycle + '\'' +
        ", role=" + role +
        "} " + super.toString();
  }
}
