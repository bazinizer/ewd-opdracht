package com.example.olympics;

import org.eclipse.persistence.internal.oxm.schema.model.List;
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

import domain.Sport;
import domain.Wedstrijd;
import service.SportService;
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
    @Autowired
    private WedstrijdValidator wedstrijdValidator;
    @Autowired
    private SportService sportService;
    

    @GetMapping
    public String listWedstrijden(Model model) {
        model.addAttribute("wedstrijden", wedstrijdService.findAll());
        return "wedstrijden"; 
    }

    @GetMapping("/{sportId}")
    public String getWedstrijd(@PathVariable Long sportId, Model model) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("wedstrijden", wedstrijdService.findBySportId(sportId));
        model.addAttribute("sport", sportService.findById(sportId)); // Voeg toe om de sportnaam te tonen
        model.addAttribute("isAdmin", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

        return "wedstrijden-detail";
    }
}

