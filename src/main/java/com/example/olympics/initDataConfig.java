package com.example.olympics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import domain.MyUser;
import domain.Role;
import domain.Sport;
import domain.Stadium;
import domain.Ticket;
import domain.Wedstrijd;
import repository.MyUserRepository;
import repository.SportRepository;
import service.SportService;
import service.StadiumService;
import service.TicketService;
import service.WedstrijdService;

@Configuration
public class initDataConfig implements CommandLineRunner {

    @Autowired
    private MyUserRepository myUserRepository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private SportService sportService;
    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private WedstrijdService wedstrijdService;
    @Autowired
    private StadiumService stadiumService;
    @Autowired
    private TicketService ticketService;

    @Override
    public void run(String... args) {
        var user = MyUser.builder()
            .username("Baz")
            .role(Role.USER)
            .password(encoder.encode("123"))
            .build();
        var userWithNoTickets = MyUser.builder()
            .username("Mark")
            .role(Role.USER)
            .password(encoder.encode("123"))
            .build();
        var admin = MyUser.builder()
            .username("admin")
            .role(Role.ADMIN)
            .password(encoder.encode("123"))
            .build();

        List<MyUser> userList = Arrays.asList(admin, user, userWithNoTickets);
        myUserRepository.saveAll(userList);

        List<Sport> sports = new ArrayList<>();
        sports.add(new Sport("Atletiek", new HashSet<>(Arrays.asList("100m sprint", "Marathon"))));
        sports.add(new Sport("Zwemmen", new HashSet<>(Arrays.asList("50m vrije slag", "200m schoolslag"))));
        sports.add(new Sport("Gymnastiek", new HashSet<>(Arrays.asList("Rekstok", "Vloer"))));
        sports.add(new Sport("Judo", new HashSet<>(Arrays.asList("Lichtgewicht", "Zwaargewicht"))));
        sports.add(new Sport("Boogschieten", new HashSet<>(Arrays.asList("Recurve", "Compound"))));
        sportRepository.saveAll(sports);

        List<Stadium> stadiums = new ArrayList<>(Arrays.asList(
            new Stadium("Olympisch Stadion", 50000),
            new Stadium("Aquatics Center", 15000)
        ));
        stadiumService.saveAll(stadiums);

        // Voorbeeld voor Atletiek en Zwemmen
        Sport atletiek = sports.get(0);
        Sport zwemmen = sports.get(1);
        Stadium stadion = stadiums.get(0);

        // Creëer wedstrijden voor Atletiek
        wedstrijdService.save(new Wedstrijd(atletiek, stadion, LocalDateTime.now().plusDays(1), 50, 20.0, atletiek.getDisciplines(), generateOlympicNumber(), generateOlympicNumber()));

        // Creëer wedstrijden voor Zwemmen
        wedstrijdService.save(new Wedstrijd(zwemmen, stadion, LocalDateTime.now().plusDays(2), 30, 25.0, zwemmen.getDisciplines(), generateOlympicNumber(), generateOlympicNumber()));
        
        // Meer wedstrijden kunnen op soortgelijke wijze worden toegevoegd
    }

    private int generateOlympicNumber() {
        return new Random().nextInt(90000) + 10000; // Zorgt voor een nummer tussen 10000 en 99999
    }
}
