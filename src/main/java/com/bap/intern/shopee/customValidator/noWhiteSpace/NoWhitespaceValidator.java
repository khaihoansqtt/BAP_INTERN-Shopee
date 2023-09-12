package com.bap.intern.shopee.customValidator.noWhiteSpace;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoWhitespaceValidator implements ConstraintValidator<NoWhiteSpace, String> {

  @Override
  public void initialize(NoWhiteSpace contactNumber) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext cxt) {
	  if (value == null) return true;
      return !value.trim().isEmpty();
  }

}
