package com.cat.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cat.web.validator.ActionFeedbackValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ActionFeedbackValidator.class})
public @interface ActionFeedbackConstraint {

	String message() default "无效的行动码";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
