package com.cat.annotation;

import com.cat.module.enums.Role;

import java.lang.annotation.*;

/**
 * Created by cyuan on 2018/10/12.
 * 如果同时include和exclude值,exclude优先
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAuth {
    /**
     * 表示可以进入的角色
     * @return
     */
    Role[] include() default {};

    /**
     * 表示不可进入的角色
     * @return
     */
    Role[] exclude() default {};
}
