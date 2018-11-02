package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Created by jxli on 2018/10/29.
 */
@Entity
@Table(name = "t_role_permission")
@IdClass(RolePermission.class)
public class RolePermission extends AuditingEntity {
  @Id
  private Long roleId;
  @Id
  private String permission;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }
}
