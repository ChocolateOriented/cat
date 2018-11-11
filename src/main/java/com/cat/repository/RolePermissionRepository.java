package com.cat.repository;

import com.cat.module.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

/**
 * Created by jxli on 2018/9/19.
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission,RolePermission> {

}
