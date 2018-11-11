package com.cat.repository;

import com.cat.module.entity.Role;
import com.cat.module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by jxli on 2018/9/19.
 */
public interface RoleRepository extends JpaRepository<Role,Long>{

}
