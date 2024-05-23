package com.example.olympics;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.MyUser;
import domain.Ticket;
import domain.Wedstrijd;
import jakarta.validation.Valid;
import service.MyUserService;
import service.SportService;
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
    private MyUserService myUserService;

    @Autowired
    private TicketPurchaseValidator purchaseValidator;
    @Autowired
    private SportService sportService;

    @GetMapping("/{wedstrijdId}/koopTicket")
    public String showTicketPurchase(@PathVariable Long wedstrijdId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
        model.addAttribute("ticket", new Ticket());

        return "koopTicket";
    }

    @PostMapping("/{wedstrijdId}/koopTicket")
    public String buyTickets(@PathVariable Long wedstrijdId, @Valid Ticket ticket, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Long userId = myUserService.getUserIdByUsername(userName);
        MyUser user = myUserService.findByUsername(userName);

        Wedstrijd wedstrijd = wedstrijdService.findById(wedstrijdId);

        ticket.setUser(user);
        ticket.setWedstrijd(wedstrijd);
        

        
        purchaseValidator.validate(ticket, result);
        if (result.hasErrors()) {
            int ticketsAlreadyBought = ticketService.getTicketsBoughtForWedstrijdByUser(wedstrijdId, userId);
            model.addAttribute("wedstrijd", wedstrijd);
            model.addAttribute("ticketsAlreadyBought", ticketsAlreadyBought);
            model.addAttribute("remainingTickets", Math.min(20 - ticketsAlreadyBought, wedstrijd.getVrijePlaatsen()));
            model.addAttribute("formattedDate", wedstrijd.getDatumTijd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            return "koopTicket"; 
        }

        
        ticketService.purchaseTickets(userId, wedstrijdId, ticket.getAantal());

        return "redirect:/sport";
    }
}
