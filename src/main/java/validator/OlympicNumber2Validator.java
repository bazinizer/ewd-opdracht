package validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Wedstrijd;
import repository.WedstrijdRepository;
import service.WedstrijdService;

public class OlympicNumber2Validator implements Validator {
	
	@Autowired
	private WedstrijdService service;
	

    @Override
    public boolean supports(Class<?> clazz) {
        return Wedstrijd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	List<Integer> alreadyexists2 = service.findAll().stream().map(Wedstrijd::getOlympicNumber2).collect(Collectors.toList());

        Wedstrijd wedstrijd = (Wedstrijd) target;
        int olympicNumber1 = wedstrijd.getOlympicNumber1();
        int olympicNumber2 = wedstrijd.getOlympicNumber2();
        String number = String.valueOf(olympicNumber2);


        if (Math.abs(olympicNumber1 - olympicNumber2) > 1000) {
            errors.rejectValue("olympicNumber2", "olympicNumber2.range.invalid", "Olympisch-nummer2 moet binnen 1000 nummers van nummer1 liggen.");
        }
        if (number.charAt(0) == '0') {
            errors.rejectValue("olympicNumber2", "olympicNumber.0.invalid", "Olympisch nummer moet uit 5 cijfers bestaan, mag niet met 0 beginnen, en het eerste en het laatste cijfer moeten verschillend zijn.");

        }
        if(olympicNumber1 == olympicNumber2) {
            errors.rejectValue("olympicNumber2", "olympicNumber.same.invalid", "Olympisch nummer moet uit 5 cijfers bestaan, mag niet met 0 beginnen, en het eerste en het laatste cijfer moeten verschillend zijn.");

        }
        if(alreadyexists2.contains(olympicNumber2)) {
            errors.rejectValue("olympicNumber2", "olympicNumber.exists.invalid", "Olympisch nummer moet uit 5 cijfers bestaan, mag niet met 0 beginnen, en het eerste en het laatste cijfer moeten verschillend zijn.");

        }
        
        
    }
}
