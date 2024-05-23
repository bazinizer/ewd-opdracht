package validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import validator.OlympicNumber2Validator;

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

@Constraint(validatedBy = OlympicNumber2Validator.class)
@Target({METHOD,FIELD})
@Retention(RUNTIME)
public @interface OlympicNumber2Validation {
    String message() default "{olympicNumber2.range.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
