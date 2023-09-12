package com.bap.intern.shopee.customValidator.noWhiteSpace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NoWhitespaceValidator.class)
@Target( {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoWhiteSpace {
    String message() default "Invalid value, value must only have not white space";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
