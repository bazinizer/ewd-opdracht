package com.example.olympics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import domain.Wedstrijd;
import exceptions.SportNotFoundException;
import exceptions.WedstrijdNotFoundException;
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
    
    
    // handelers zodat ik geen error krijg die de test laat falen
    @ExceptionHandler(WedstrijdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleWedstrijdNotFoundException(WedstrijdNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleSportNotFoundException(SportNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    

}
