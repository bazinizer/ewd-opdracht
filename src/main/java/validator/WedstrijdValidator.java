package validator;

import domain.Wedstrijd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import service.StadiumService;
import service.WedstrijdService;

@Component
public class WedstrijdValidator implements Validator {

    @Autowired
    private WedstrijdService wedstrijdService;

    @Autowired
    private StadiumService stadiumService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Wedstrijd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Wedstrijd wedstrijd = (Wedstrijd) target;

        // Validate disciplines
        if (wedstrijd.getDisciplines() != null && wedstrijd.getDisciplines().size() > 2) {
            errors.rejectValue("disciplines", "disciplines.max.exceeded");
        }
        if (wedstrijd.getDisciplines() != null && wedstrijd.getDisciplines().stream().distinct().count() != wedstrijd.getDisciplines().size()) {
            errors.rejectValue("disciplines", "disciplines.duplicate");
        }

//        // Validate stadium existence
//        if (wedstrijd.getStadium() == null || !stadiumService.existsById(wedstrijd.getStadium().getId())) {
//            errors.rejectValue("stadium", "stadium.notfound");
//        }

        // Validate olympicNumber1 uniqueness
        if (wedstrijdService.existsByOlympicNumber1(wedstrijd.getOlympicNumber1())) {
            errors.rejectValue("olympicNumber1", "olympicNumber.duplicate");
        }

        // Validate olympicNumber2 range
        int olympicNumber1 = wedstrijd.getOlympicNumber1();
        int olympicNumber2 = wedstrijd.getOlympicNumber2();
        if (Math.abs(olympicNumber1 - olympicNumber2) > 1000) {
            errors.rejectValue("olympicNumber2", "olympicNumber2.range.invalid");
        }
    }
}

