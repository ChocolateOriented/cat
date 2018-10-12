package com.cat.repository;

import com.cat.module.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jxli on 2018/9/19.
 */
public interface UserRepository extends JpaRepository<User,String> {

  User findTopByEmail(String email);
}
