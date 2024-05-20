package com.example.olympics;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Wedstrijd;
import service.MyUserService;
import service.TicketService;
import service.WedstrijdService;
import validator.TicketPurchaseValidator;

@Controller
@RequestMapping("/wedstrijden")
public class TicketPurchaseController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private WedstrijdService wedstrijdService;
    @Autowired
    private  MyUserService myUserService;
    @Autowired
    private  TicketPurchaseValidator purchaseValidator;

    @GetMapping("/{wedstrijdId}/koopTicket")
    public String showTicketPurchase(@PathVariable Long wedstrijdId, Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Long userId = myUserService.getUserIdByUsername(userName);
        
        Wedstrijd wedstrijd = wedstrijdService.findById(wedstrijdId);
        int ticketsAlreadyBought = ticketService.getTicketsBoughtForWedstrijdByUser(wedstrijdId, userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = wedstrijd.getDatumTijd().format(formatter);
        model.addAttribute("formattedDate", formattedDate);

        model.addAttribute("wedstrijd", wedstrijd);
        model.addAttribute("ticketsAlreadyBought", ticketsAlreadyBought);
        model.addAttribute("remainingTickets", Math.min(20 - ticketsAlreadyBought, wedstrijd.getVrijePlaatsen()));

        return "koopTicket";
    }

    @PostMapping("/{wedstrijdId}/koopTicket")
    public String buyTickets(@PathVariable Long wedstrijdId, @RequestParam int aantal, Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Long userId = myUserService.getUserIdByUsername(userName);

        // Validate the ticket purchase attempt
        String validationMessage = purchaseValidator.validateTicketPurchase(userId, wedstrijdId, aantal);
        if (validationMessage != null) {
            model.addAttribute("error", validationMessage);
            return showTicketPurchase(wedstrijdId, model); // Reload the purchase page with an error message
        }

        // Proceed with ticket purchase if validation is successful
        ticketService.purchaseTickets(userId, wedstrijdId, aantal);
        model.addAttribute("message", aantal + " tickets succesvol aangekocht");
        return "redirect:/wedstrijden/" + wedstrijdId;
    }
}
