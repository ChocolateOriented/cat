package com.cat.web;

import com.cat.module.entity.User;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by jxli on 2018/3/8.
 */
public abstract class BaseController {

	public static final String DEFAULT_PAGE_SIZE = "10" ;
	public static final String DEFAULT_PAGE_NUM = "1";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected User getCurrentUser(){
		Subject subject = SecurityUtils.getSubject();
		if (null == subject){
			return null;
		}
		User user = (User) subject.getPrincipal();
		return user;
	}

	/**
	 * @Description 获取字段错误信息
	 * @param bindingResul
	 * @return java.lang.String
	 */
	protected static String getFieldErrorsMessages(BindingResult bindingResul){
		if (!bindingResul.hasErrors()){
			return "";
		}

		StringBuilder errorMsg = new StringBuilder();
		List<FieldError> fieldErrors = bindingResul.getFieldErrors();
		if (fieldErrors == null){
			return "";
		}

		for (int i = 0; i < fieldErrors.size(); i++) {
			if (i > 0){
				errorMsg.append(", ");
			}
			errorMsg.append(fieldErrors.get(i).getDefaultMessage());
		}
		return errorMsg.toString();
	}

}
