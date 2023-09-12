package com.bap.intern.shopee.customValidator.intArrayRange;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = IntArrayRangeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntArrayRange {
    String message() default "Array value is invalid";
    int min() default 0;
    int max() default 10;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
