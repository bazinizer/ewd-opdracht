package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import validation.DateTimeValidation;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeValidator implements ConstraintValidator<DateTimeValidation, LocalDateTime> {

    private final LocalDateTime MIN_DATE = LocalDateTime.of(2024, 7, 26, 0, 0);
    private final LocalDateTime MAX_DATE = LocalDateTime.of(2024, 8, 11, 23, 59);
    private final LocalTime MIN_TIME = LocalTime.of(8, 0);

    @Override
    public void initialize(DateTimeValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return !value.isBefore(MIN_DATE) && !value.isAfter(MAX_DATE) && !value.toLocalTime().isBefore(MIN_TIME);
    }
}
