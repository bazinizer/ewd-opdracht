package com.example.olympics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import service.WedstrijdService;

@Controller
@RequestMapping("/wedstrijden")
public class WedstrijdController {

    @Autowired
    private WedstrijdService wedstrijdService;

    @GetMapping
    public String listWedstrijden(Model model) {
        model.addAttribute("wedstrijden", wedstrijdService.findAll());
        return "wedstrijden"; // This should be the name of your Thymeleaf template
    }

    @GetMapping("/{id}")
    public String getWedstrijd(@PathVariable Long id, Model model) {
        model.addAttribute("wedstrijd", wedstrijdService.findById(id));
        return "wedstrijden-detail"; 
    }
}
