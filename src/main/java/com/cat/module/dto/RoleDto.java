package com.cat.module.dto;

import com.cat.module.entity.Role;
import com.cat.module.entity.RolePermission;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxli on 2018/11/9.
 */
public class RoleDto {
  private Long id;
  @NotBlank
  private String name;
  private List<String> permissions;

  public RoleDto() {
  }

  public RoleDto(Role role) {
    this.id = role.getId();
    this.name = role.getName();
    List<RolePermission> rolePermissions = role.getPermissions();
    if (CollectionUtils.isEmpty(rolePermissions)) {
      return;
    }
    this.permissions = rolePermissions.stream().map(rolePermission -> (rolePermission
        .getPermission())).collect(Collectors.toList());

    permissions.forEach(permission -> System.out.println(permission));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }
}

