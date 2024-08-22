package com.twigcodes.cms.validators.time;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeComparisonValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeComparison {
    String message() default "Invalid time comparison";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();

    String secondField();

    ComparisonMode mode() default ComparisonMode.BEFORE;

    enum ComparisonMode {
        BEFORE, // firstField 应该早于 secondField
        AFTER   // firstField 应该晚于 secondField
    }
}
