package validator;

import domain.Wedstrijd;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import repository.StadiumRepository;
import repository.WedstrijdRepository;
import java.time.LocalDateTime;

public class WedstrijdValidator implements Validator {

    private StadiumRepository stadiumRepository;
    private WedstrijdRepository wedstrijdRepository;

    public WedstrijdValidator(StadiumRepository stadiumRepository, WedstrijdRepository wedstrijdRepository) {
        this.stadiumRepository = stadiumRepository;
        this.wedstrijdRepository = wedstrijdRepository;
    }

    public WedstrijdValidator() {
	}

	@Override
    public boolean supports(Class<?> clazz) {
        return Wedstrijd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Wedstrijd wedstrijd = (Wedstrijd) target;

        // Datum validatie
        if (wedstrijd.getDatumTijd() == null) {
            errors.rejectValue("datumTijd", "wedstrijd.datum.required");
        } else {
            if (wedstrijd.getDatumTijd().isBefore(LocalDateTime.of(2024, 7, 26, 8, 0)) ||
                wedstrijd.getDatumTijd().isAfter(LocalDateTime.of(2024, 8, 11, 23, 59))) {
                errors.rejectValue("datumTijd", "wedstrijd.datum.range.invalid");
            }
        }

//        // Stadium naam validatie
//        if (wedstrijd.getStadium() != null && !stadiumRepository.existsById(wedstrijd.getStadium().getId())) {
//            errors.rejectValue("stadium", "wedstrijd.stadium.invalid");
//        }

        // Olympisch nummer validatie
//        validateOlympicNumber(wedstrijd.getOlympischNummer1(), "olympicNumber1", errors);
//        if (wedstrijd.getOlympicNumber2() != null) {
//            if (Math.abs(wedstrijd.getOlympischNummer1() - wedstrijd.getOlympicNumber2()) > 1000) {
//                errors.rejectValue("olympicNumber2", "wedstrijd.olympicNumber2.range.invalid");
//            }
//        }

        // Ticketprijs validatie
        if (wedstrijd.getPrijsPerTicket() <= 0 || wedstrijd.getPrijsPerTicket() >= 150) {
            errors.rejectValue("prijsPerTicket", "wedstrijd.prijsPerTicket.range.invalid");
        }

        // Aantal plaatsen validatie
        if (wedstrijd.getVrijePlaatsen() <= 0 || wedstrijd.getVrijePlaatsen() >= 50) {
            errors.rejectValue("vrijePlaatsen", "wedstrijd.vrijePlaatsen.range.invalid");
        }
    }

    private void validateOlympicNumber(int number, String field, Errors errors) {
        if (number < 10000 || number > 99999) {
            errors.rejectValue(field, "wedstrijd.olympicNumber.format.invalid");
        } else if (Integer.toString(number).startsWith("0")) {
            errors.rejectValue(field, "wedstrijd.olympicNumber.start.invalid");
        } else if (Integer.toString(number).charAt(0) == Integer.toString(number).charAt(4)) {
            errors.rejectValue(field, "wedstrijd.olympicNumber.digits.different.invalid");
        } else if (wedstrijdRepository.existsByOlympicNumber1(number)) {
            errors.rejectValue(field, "wedstrijd.olympicNumber.duplicate");
        }
    }
}
