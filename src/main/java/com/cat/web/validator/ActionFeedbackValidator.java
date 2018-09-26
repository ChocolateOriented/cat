package com.cat.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cat.annotation.ActionFeedbackConstraint;
import com.cat.module.enums.ActionFeedback;

/**
 * 校验行动码参数
 */
public class ActionFeedbackValidator implements ConstraintValidator<ActionFeedbackConstraint, Enum<ActionFeedback>> {

	@Override
	public void initialize(ActionFeedbackConstraint constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Enum<ActionFeedback> value, ConstraintValidatorContext context) {
		return value != null;
	}

}
