package validator;

import jakarta.validation.ConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Wedstrijd;

public class DateTimeValidator implements Validator{


	@Override
	public boolean supports(Class<?> clazz) {
		
		return Wedstrijd.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	Wedstrijd wedstrijd = (Wedstrijd) target;
	    LocalDateTime MIN_DATE = LocalDateTime.of(2024, 7, 26, 8, 0);
	    LocalDateTime MAX_DATE = LocalDateTime.of(2024, 8, 11, 23, 59);
	    
	    LocalDateTime wedstrijdDatums = wedstrijd.getDatumTijd();
	    
	    if (wedstrijdDatums == null) {
	    	return;
	    }
	    if (wedstrijdDatums.isBefore(MIN_DATE) || wedstrijdDatums.isAfter(MAX_DATE)) {
	    	errors.rejectValue("datumTijd", "datum.range.invalid","foute tijd");
	    }
		
	}
}
