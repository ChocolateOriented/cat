package com.cat.module.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by jxli on 2018/10/29.
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity {

  private String name;

  private Boolean enabled;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "roleId")
  private List<RolePermission> permissions;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public List<RolePermission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<RolePermission> permissions) {
    this.permissions = permissions;
  }

}
