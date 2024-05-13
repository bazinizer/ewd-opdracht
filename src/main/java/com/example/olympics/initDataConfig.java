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
        sports.add(new Sport("Voetbal"));
        sports.add(new Sport("Hockey"));
        sports.add(new Sport("Tennis"));
        sports.add(new Sport("Rugby"));
        sports.add(new Sport("Bolder"));
        sportRepository.saveAll(sports);

        List<Stadium> stadiums = new ArrayList<>(Arrays.asList(
            new Stadium("Stadium 1", 50000),
            new Stadium("Stadium 2", 30000)
        ));
        stadiumService.saveAll(stadiums);

        Sport voetbal = sportService.findAll().stream().filter(s -> s.getNaam().equals("Voetbal")).findFirst().orElse(null);
        Sport tennis = sportService.findAll().stream().filter(s -> s.getNaam().equals("Tennis")).findFirst().orElse(null);
        Stadium stadium1 = stadiumService.findAll().get(0);
        Stadium stadium2 = stadiumService.findAll().get(1);

        HashSet<String> disciplinesVoetbal = new HashSet<>(Arrays.asList("Mannen", "Vrouwen"));
        Wedstrijd wedstrijdVoetbal = new Wedstrijd(voetbal, stadium1, LocalDateTime.now().plusDays(1), 100, 50, disciplinesVoetbal, generateOlympicNumber(), generateOlympicNumber());

        HashSet<String> disciplinesTennis = new HashSet<>(Arrays.asList("Enkel", "Dubbel"));
        Wedstrijd wedstrijdTennis = new Wedstrijd(tennis, stadium2, LocalDateTime.now().plusDays(2), 50, 30, disciplinesTennis, generateOlympicNumber(), generateOlympicNumber());

        wedstrijdService.saveAll(Arrays.asList(wedstrijdVoetbal, wedstrijdTennis));

        Ticket ticket1 = new Ticket(wedstrijdVoetbal, user, 2, 100.0);
        Ticket ticket2 = new Ticket(wedstrijdTennis, user, 1, 30.0);
        ticketService.saveAll(Arrays.asList(ticket1, ticket2));
    }

    private int generateOlympicNumber() {
        // Generate a valid Olympic number here, ensuring it adheres to your rules
        return new Random().nextInt(90000) + 10000; // Example to generate a number between 10000 and 99999
    }
}