package com.twigcodes.cms.validators.time;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class TimeComparisonValidator implements ConstraintValidator<TimeComparison, Object> {

    private String firstField;
    private String secondField;
    private TimeComparison.ComparisonMode mode;

    @Override
    public void initialize(TimeComparison constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
        this.mode = constraintAnnotation.mode();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field firstField = value.getClass().getDeclaredField(this.firstField);
            Field secondField = value.getClass().getDeclaredField(this.secondField);
            firstField.setAccessible(true);
            secondField.setAccessible(true);

            LocalDateTime firstTime = (LocalDateTime) firstField.get(value);
            LocalDateTime secondTime = (LocalDateTime) secondField.get(value);

            if (firstTime == null || secondTime == null) {
                return true; // 如果有一个时间为空，不做校验
            }

            if (mode == TimeComparison.ComparisonMode.BEFORE) {
                return firstTime.isBefore(secondTime);
            } else {
                return firstTime.isAfter(secondTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

