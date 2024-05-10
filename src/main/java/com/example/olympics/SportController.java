package com.example.olympics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Sport;
import service.SportService;
@RequestMapping("/sport")
@Controller
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public String showSports(Model model) {
        List<Sport> sports = sportService.findAll();
        model.addAttribute("sport", sports);
        return "sport";
    }
}
