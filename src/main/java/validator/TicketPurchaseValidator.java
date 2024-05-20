package validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.Wedstrijd;
import service.TicketService;
import service.WedstrijdService;

@Component
public class TicketPurchaseValidator {

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
    public String validateTicketPurchase(Long userId, Long wedstrijdId, int aantal) {
        if (aantal <= 0) {
            return "Het aantal te kopen tickets moet groter zijn dan 0.";
        }

        int ticketsAlreadyBoughtForThisWedstrijd = ticketService.getTotalTicketsBoughtForWedstrijdByUser(wedstrijdId, userId);
        System.out.println(ticketsAlreadyBoughtForThisWedstrijd);
        if (ticketsAlreadyBoughtForThisWedstrijd + aantal > 20) {
            return "Je kunt niet meer dan 20 tickets voor deze specifieke wedstrijd kopen.";
        }

        int totalTicketsBought = ticketService.getTotalTicketsBoughtByUser(userId);
        if (totalTicketsBought + aantal > 100) {
            return "Je kunt in totaal niet meer dan 100 tickets kopen voor alle wedstrijden.";
        }

        Wedstrijd wedstrijd = wedstrijdService.findById(wedstrijdId);
        if (wedstrijd == null) {
            return "De geselecteerde wedstrijd bestaat niet.";
        }
        
        if (aantal > wedstrijd.getVrijePlaatsen()) {
            return "Er zijn niet genoeg vrije plaatsen beschikbaar voor deze aankoop.";
        }

        return null; // No errors, validation passed
    }
}
