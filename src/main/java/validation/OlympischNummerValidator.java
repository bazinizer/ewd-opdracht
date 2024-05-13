package validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OlympischNummerValidator implements ConstraintValidator<OlympischNummerValidation, String> {
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.matches("^[1-9][0-9]{4}$") && value.charAt(0) != value.charAt(4);
    }
}
