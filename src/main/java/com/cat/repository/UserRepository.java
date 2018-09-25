package com.cat.repository;

import com.cat.module.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by jxli on 2018/9/19.
 */
public interface UserRepository extends PagingAndSortingRepository<User,String> {

}
