package com.example.olympics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Wedstrijd;
import service.SportService;
import service.WedstrijdService;

@RestController
@RequestMapping("/rest")
public class WedstrijdRestController {
	
    @Autowired
    private WedstrijdService wedstrijdService;
    
    
    @GetMapping("/wedstrijdAvailability/{id}")
    public Wedstrijd getWedstrijdAvailability(@PathVariable("id") Long wedstrijdId) {
        
        return wedstrijdService.findById(wedstrijdId);
    }

    @GetMapping("/sport/{id}")
    public List<Wedstrijd> getWedstrijdenBySport(@PathVariable("id") Long sportId) {
        return wedstrijdService.findBySportId(sportId);
    }
    
    

}
