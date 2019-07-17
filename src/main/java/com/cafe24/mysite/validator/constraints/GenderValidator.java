package com.cafe24.mysite.validator.constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cafe24.mysite.validator.ValidGender;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {
	private Pattern pattern = Pattern.compile("MALE|FEMAIL|NONE");
	
	@Override
	public void initialize(ValidGender constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.length() == 0 || "".equals(value))
			return false;
		
		return pattern.matcher(value).matches();
	}

}
