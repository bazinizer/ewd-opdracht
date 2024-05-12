package com.example.olympics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import service.TicketService;
import service.WedstrijdService;

@Controller
@RequestMapping("/wedstrijden")
public class WedstrijdController {

    @Autowired
    private WedstrijdService wedstrijdService;
    
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String listWedstrijden(Model model) {
        model.addAttribute("wedstrijden", wedstrijdService.findAll());
        return "wedstrijden"; 
    }

    @GetMapping("/{id}")
    public String getWedstrijd(@PathVariable Long id, Model model) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("wedstrijd", wedstrijdService.findById(id));
        model.addAttribute("tickets", ticketService.countByUserUsernameAndWedstrijdId(username, id));
        model.addAttribute("isAdmin", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        return "wedstrijden-detail";
    }
}

