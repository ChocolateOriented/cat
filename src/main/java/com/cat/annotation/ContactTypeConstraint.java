package com.cat.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cat.web.validator.ContactTypeValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ContactTypeValidator.class})
public @interface ContactTypeConstraint {

	String message() default "无效的联系人类型";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
