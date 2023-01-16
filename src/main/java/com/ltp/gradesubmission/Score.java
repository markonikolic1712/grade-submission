package com.ltp.gradesubmission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ScoreValidator.class) // sa ovime kazemo da ce Score interfejs biti constraint validacija a da ce logika za proveru biti u klasi ScoreValidator.class
public @interface Score { 

    // kada se definise constraint anotacija ovo dole mora da se definise
    String message() default "Invalid data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
