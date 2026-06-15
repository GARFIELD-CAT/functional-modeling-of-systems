package ru.utmn.dayagunov.functional_modeling_of_systems.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MigrantFieldValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MigrantField {
    String message() default "Недопустимое поле мигранта: '${validatedValue}'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}