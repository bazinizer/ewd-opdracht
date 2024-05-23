package validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import validator.OlympicNumber1Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = OlympicNumber1Validator.class)
@Target({METHOD,FIELD})
@Retention(RUNTIME)
public @interface OlympicNumber1Validation {
    String message() default "{olympicNumber.format.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
