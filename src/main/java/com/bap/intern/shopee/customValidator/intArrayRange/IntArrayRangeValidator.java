package com.bap.intern.shopee.customValidator.intArrayRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntArrayRangeValidator implements ConstraintValidator<IntArrayRange, int[]> {

    private int min;
    private int max;

    @Override
    public void initialize(IntArrayRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(int[] array, ConstraintValidatorContext context) {
        if (array == null) {
            return true;
        }
        for (int element : array) {
            if (element < min || element > max) {
                return false;
            }
        }
        return true;
    }
}
