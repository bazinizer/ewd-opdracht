package validation;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WedstrijdDatumValidator implements ConstraintValidator<WedstrijdDatum, LocalDateTime> {
    private static final LocalDateTime START_DATE = LocalDateTime.of(2024, 7, 26, 8, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(2024, 8, 11, 23, 59);

    public void initialize(WedstrijdDatum constraintAnnotation) {
        // Initialization can be used to configure annotation parameters if needed
    }

    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return !value.isBefore(START_DATE) && !value.isAfter(END_DATE) && value.getHour() >= 8;
    }
}
