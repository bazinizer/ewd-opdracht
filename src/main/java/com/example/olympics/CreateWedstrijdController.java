package com.example.olympics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Sport;
import domain.Wedstrijd;

import service.SportService;
import service.StadiumService;
import service.WedstrijdService;
import validator.DateTimeValidator;
import validator.OlympicNumber1Validator;
import validator.OlympicNumber2Validator;

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
    private DateTimeValidator dateTimeValidator;
    @Autowired
    private OlympicNumber1Validator olympicNumber1Validator;
    @Autowired
    private OlympicNumber2Validator olympicNumber2Validator;

    @GetMapping("/{sportId}/create")
    public String showCreateForm(@PathVariable Long sportId, Model model) {
        Sport sport = sportService.findById(sportId);
        Wedstrijd newWedstrijd = new Wedstrijd();
        newWedstrijd.setSport(sport);
        model.addAttribute("wedstrijd", newWedstrijd);
        model.addAttribute("stadiums", stadiumService.findAll());
        model.addAttribute("disciplines", wedstrijdService.findDisciplinesBySportId(sportId));
        model.addAttribute("sport", sportService.findById(sportId));
        return "create-wedstrijd";
    }

    @PostMapping("/{sportId}/create")
    public String createWedstrijd(@Validated Wedstrijd wedstrijd, BindingResult result, Model model,@PathVariable Long sportId, Authentication authentication) {
        dateTimeValidator.validate(wedstrijd, result);
        olympicNumber1Validator.validate(wedstrijd, result);
        olympicNumber2Validator.validate(wedstrijd, result);
        if (result.hasErrors()) {
            model.addAttribute("stadiums", stadiumService.findAll());
            model.addAttribute("sport", sportService.findById(sportId));
            model.addAttribute("disciplines", wedstrijdService.findDisciplinesBySportId(sportId));
            return "create-wedstrijd";
        }
        wedstrijd.setSport(sportService.findById(sportId));
        wedstrijdService.save(wedstrijd);
        return "redirect:/sport";
    }
}
