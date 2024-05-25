package com.example.olympics;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.util.ReflectionTestUtils;

import domain.Sport;
import domain.Ticket;
import domain.Wedstrijd;
import service.SportService;
import service.TicketService;
import service.WedstrijdService;

@SpringBootTest
@AutoConfigureMockMvc
public class SportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportService sportService;

    @MockBean
    private TicketService ticketService;


    @InjectMocks
    private SportController sportController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(sportController, "sportService", sportService);
        ReflectionTestUtils.setField(sportController, "ticketService", ticketService);

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowSportsWithUserRole() throws Exception {
        Sport sport1 = new Sport();
        sport1.setId(1L);
        sport1.setNaam("Atletiek");

        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        wedstrijd1.setSport(sport1);
        wedstrijd1.setDatumTijd(LocalDateTime.of(2024, 7, 27, 15, 56));

        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setWedstrijd(wedstrijd1);
        ticket1.setAantal(2);

        when(sportService.findAll()).thenReturn(Arrays.asList(sport1));
        when(ticketService.findTicketsByUser("user")).thenReturn(Arrays.asList(ticket1));
        when(ticketService.hasTickets("user")).thenReturn(true);

        mockMvc.perform(get("/sport"))
               .andExpect(status().isOk())
               .andExpect(view().name("sport"))
               .andExpect(model().attributeExists("sport"))
               .andExpect(model().attributeExists("username"))
               .andExpect(model().attributeExists("tickets"))
               .andExpect(model().attributeExists("hasTickets"))
               .andExpect(model().attribute("sport", Arrays.asList(sport1)))
               .andExpect(model().attribute("username", "user"))
               .andExpect(model().attribute("tickets", Arrays.asList(ticket1)))
               .andExpect(model().attribute("hasTickets", true));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAccessWithAdminRole() throws Exception {
        mockMvc.perform(get("/sport"))
               .andExpect(status().isOk())
               .andExpect(view().name("sport"));
    }

    @Test
    @WithAnonymousUser
    public void testNoAccessAnonymous() throws Exception {
        mockMvc.perform(get("/sport"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }
}
