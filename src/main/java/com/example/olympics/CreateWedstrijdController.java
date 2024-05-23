package com.example.olympics;

import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.validation.Valid;
import service.SportService;
import service.StadiumService;
import service.WedstrijdService;
import validator.WedstrijdValidator;

@Controller
@RequestMapping("/wedstrijden")
public class CreateWedstrijdController {

    @Autowired
    private SportService sportService;
    @Autowired
    private StadiumService stadiumService;
    @Autowired
    private WedstrijdService wedstrijdService;
    @Autowired
    private WedstrijdValidator wedstrijdValidator;
    
    @GetMapping("/{sportId}/create")
    public String showCreateForm(@PathVariable Long sportId, Model model) {
        Sport sport = sportService.findById(sportId);
        Wedstrijd newWedstrijd = new Wedstrijd();
        newWedstrijd.setSport(sport);
        model.addAttribute("wedstrijd", newWedstrijd);
        model.addAttribute("stadiums", stadiumService.findAll());
        model.addAttribute("disciplines", wedstrijdService.findDisciplinesBySportId(sportId)); 
        return "create-wedstrijd";
    }

    @PostMapping("/{sportId}/create")
    public String createWedstrijd(@PathVariable Long sportId, @Valid Wedstrijd wedstrijd, BindingResult result, Model model) {
    	
        wedstrijdValidator.validate(wedstrijd, result);
        if (result.hasErrors()) {
            model.addAttribute("stadiums", stadiumService.findAll());
            model.addAttribute("sport", sportService.findById(sportId));
            model.addAttribute("disciplines", wedstrijdService.findDisciplinesBySportId(sportId));
            model.addAttribute("wedstrijd", wedstrijd);
            return "create-wedstrijd";
        }
        wedstrijd.setSport(sportService.findById(sportId));
        wedstrijdService.save(wedstrijd);
//        System.out.println(wedstrijd);
        return "redirect:/sport";
    }

}
