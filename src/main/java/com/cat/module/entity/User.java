package com.cat.module.entity;

import com.cat.module.enums.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class User extends AuditingEntity {
  @Id
  private String Id;

  private Long organizationId;

  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;

  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserStatus status;

  private String collectCycle;

  @ManyToOne
  private Role role;//角色

  private Boolean autoDivision;//是否自动分案

  private String productType;

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

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
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

  public Boolean getAutoDivision() {
    return autoDivision;
  }

  public void setAutoDivision(Boolean autoDivision) {
    this.autoDivision = autoDivision;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

}
