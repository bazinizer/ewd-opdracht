package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import validation.OlympicNumber1Validation;

public class OlympicNumber1Validator implements ConstraintValidator<OlympicNumber1Validation, Integer> {

    @Override
    public void initialize(OlympicNumber1Validation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String number = String.valueOf(value);
        return number.length() == 5 && number.charAt(0) != '0' && number.charAt(0) != number.charAt(4);
    }
}
