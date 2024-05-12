package com.example.olympics;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Sport;
import domain.Ticket;
import service.SportService;
import service.TicketService;
@RequestMapping("/sport")
@Controller
public class SportController {

    @Autowired
    private SportService sportService;

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String showSports(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        List<Sport> sports = sportService.findAll();
        List<Ticket> tickets = ticketService.findTicketsByUser(currentUserName);

        model.addAttribute("sport", sports);
        model.addAttribute("username", currentUserName);
        model.addAttribute("tickets", tickets); 
        model.addAttribute("hasTickets", ticketService.hasTickets(currentUserName));


        return "sport";
    }
}


