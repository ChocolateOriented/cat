package com.cat.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cat.annotation.ContactTypeConstraint;
import com.cat.module.enums.ContactType;

/**
 * 校验联系人类型参数
 */
public class ContactTypeValidator implements ConstraintValidator<ContactTypeConstraint, Integer> {

	@Override
	public void initialize(ContactTypeConstraint constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return ContactType.valueOf(value) != null;
	}

}
