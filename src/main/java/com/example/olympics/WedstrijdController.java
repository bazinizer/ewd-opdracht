package com.example.olympics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Wedstrijd;
import service.StadiumService;
import service.TicketService;
import service.WedstrijdService;
import validator.WedstrijdValidator;

@Controller
@RequestMapping("/wedstrijden")
public class WedstrijdController {

    @Autowired
    private WedstrijdService wedstrijdService;
    
    @Autowired
    private TicketService ticketService;
    @Autowired
    private StadiumService stadiumService;

    

    @GetMapping
    public String listWedstrijden(Model model) {
        model.addAttribute("wedstrijden", wedstrijdService.findAll());
        return "wedstrijden"; 
    }

    @GetMapping("/{id}")
    public String getWedstrijd(@PathVariable Long id, Model model) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        model.addAttribute("disciplines", wedstrijdService.findDisciplinesBySportId(id));
        model.addAttribute("wedstrijd", wedstrijdService.findById(id));
        model.addAttribute("tickets", ticketService.countByUserUsernameAndWedstrijdId(username, id));
        model.addAttribute("isAdmin", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        return "wedstrijden-detail";
    }
}

