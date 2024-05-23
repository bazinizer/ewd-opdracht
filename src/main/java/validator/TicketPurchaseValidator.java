package validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Ticket;
import domain.Wedstrijd;
import service.TicketService;
import service.WedstrijdService;

@Component
public class TicketPurchaseValidator implements Validator {


    @Autowired
    private TicketService ticketService;

    @Autowired
    private WedstrijdService wedstrijdService;

    /**
     * Validates the ticket purchase request.
     *
     * @param userId The ID of the user attempting to purchase tickets.
     * @param wedstrijdId The ID of the wedstrijd for which tickets are being purchased.
     * @param aantal The number of tickets to purchase.
     * @return A string containing an error message if validation fails, or null if validation passes.
     */
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Ticket.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		Ticket request = (Ticket) target;
		
	
	
//    public String validateTicketPurchase(Long userId, Long wedstrijdId, int aantal) {
        if (request.getAantal() <= 0) {
            errors.rejectValue("aantal", "aantal.negative", "Het aantal te kopen tickets moet groter zijn dan 0.");
        }
        
        
        int ticketsAlreadyBoughtForThisWedstrijd = ticketService.getTotalTicketsBoughtForWedstrijdByUser(request.getWedstrijd().getId(), request.getUser().getId());

        if (ticketsAlreadyBoughtForThisWedstrijd + request.getAantal() > 20) {
            errors.rejectValue("aantal", "aantal.limit.exceeded", "Je kunt niet meer dan 20 tickets voor deze specifieke wedstrijd kopen.");
        }
        
        

        int totalTicketsBought = ticketService.getTotalTicketsBoughtByUser(request.getUser().getId());
        if (totalTicketsBought + request.getAantal() > 100) {
            errors.rejectValue("aantal", "aantal.total.limit.exceeded", "Je kunt in totaal niet meer dan 100 tickets kopen voor alle wedstrijden.");
        }


        Wedstrijd wedstrijd = wedstrijdService.findById(request.getWedstrijd().getId());


        if (request.getAantal() > wedstrijd.getVrijePlaatsen()) {
            errors.rejectValue("aantal", "aantal.not.enough", "Er zijn niet genoeg vrije plaatsen beschikbaar voor deze aankoop.");
            return;
        }


    }




}
