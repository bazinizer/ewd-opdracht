package validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Wedstrijd;
import service.WedstrijdService;

public class OlympicNumber1Validator implements Validator {
	@Autowired
	private WedstrijdService service;


    @Override
    public boolean supports(Class<?> clazz) {
        return Wedstrijd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	List<Integer> alreadyexists1 = service.findAll().stream().map(Wedstrijd::getOlympicNumber1).collect(Collectors.toList());

        Wedstrijd wedstrijd = (Wedstrijd) target;
        int olympicNumber1 = wedstrijd.getOlympicNumber1();

        String number = String.valueOf(olympicNumber1);
        System.out.println(number.charAt(0));
        if (number.length() != 5) {
            errors.rejectValue("olympicNumber1", "olympicNumber.format.invalid", "Olympisch nummer moet uit 5 cijfers bestaan, mag niet met 0 beginnen, en het eerste en het laatste cijfer moeten verschillend zijn.");
        }
        if (number.charAt(0) == '0') {
            errors.rejectValue("olympicNumber1", "olympicNumber.0.invalid", "Olympisch nummer moet uit 5 cijfers bestaan, mag niet met 0 beginnen, en het eerste en het laatste cijfer moeten verschillend zijn.");

        }
        if(alreadyexists1.contains(olympicNumber1)) {
            errors.rejectValue("olympicNumber1", "olympicNumber.exists.invalid", "Olympisch nummer moet uit 5 cijfers bestaan, mag niet met 0 beginnen, en het eerste en het laatste cijfer moeten verschillend zijn.");
        
        }
    }
}
