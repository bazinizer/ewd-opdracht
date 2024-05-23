package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import validation.OlympicNumber2Validation;
import domain.Wedstrijd;

public class OlympicNumber2Validator implements ConstraintValidator<OlympicNumber2Validation, Wedstrijd> {

    @Override
    public void initialize(OlympicNumber2Validation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Wedstrijd wedstrijd, ConstraintValidatorContext context) {
        if (wedstrijd == null) {
            return false;
        }
        int olympicNumber1 = wedstrijd.getOlympicNumber1();
        int olympicNumber2 = wedstrijd.getOlympicNumber2();
        return Math.abs(olympicNumber1 - olympicNumber2) <= 1000;
    }
}
