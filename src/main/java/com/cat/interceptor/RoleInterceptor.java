package com.cat.interceptor;

import com.cat.annotation.RoleAuth;
import com.cat.module.entity.User;
import com.cat.module.enums.Role;
import com.cat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by cyuan on 2018/10/12.
 */
@Component
public class RoleInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            RoleAuth methodAnnotation = h.getMethodAnnotation(RoleAuth.class);
            if (methodAnnotation == null) {
                return true;
            }
            CommonRequestContext instance = CommonRequestContext.getInstance();
            String currentUserId = instance.getCurrentUserId();
            User one = userRepository.findOne(currentUserId);
            if (one == null) {
                response.setStatus(401);
                return false;
            }
            Role role = one.getRole();

            if (methodAnnotation.include().length != 0 && !Arrays.asList(methodAnnotation.include()).contains(role)) {
                response.setStatus(401);
                return false;
            }

            if (methodAnnotation.exclude().length != 0 && Arrays.asList(methodAnnotation.exclude()).contains(role)) {
                response.setStatus(401);
                return false;
            }
        }

        return true;
    }
}
