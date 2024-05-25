package com.example.olympics;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.util.ReflectionTestUtils;

import domain.Sport;
import domain.Stadium;
import domain.Wedstrijd;
import service.SportService;
import service.StadiumService;
import service.TicketService;
import service.WedstrijdService;

@SpringBootTest
@AutoConfigureMockMvc
public class WedstrijdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WedstrijdService wedstrijdService;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private StadiumService stadiumService;

    @MockBean
    private SportService sportService;

    @InjectMocks
    private WedstrijdController wedstrijdController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(wedstrijdController, "wedstrijdService", wedstrijdService);
        ReflectionTestUtils.setField(wedstrijdController, "ticketService", ticketService);
        ReflectionTestUtils.setField(wedstrijdController, "stadiumService", stadiumService);
        ReflectionTestUtils.setField(wedstrijdController, "sportService", sportService);
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetWedstrijdWithUserRole() throws Exception {
        Long sportId = 1L;

        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        Stadium stadium = new Stadium();
        stadium.setId(1L);
        stadium.setNaam("Olympisch Stadion");

        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        wedstrijd1.setSport(sport);
        wedstrijd1.setStadium(stadium);
        wedstrijd1.setDatumTijd(LocalDateTime.of(2024, 7, 27, 15, 56));
        wedstrijd1.setPrijsPerTicket(50.0);
        wedstrijd1.setVrijePlaatsen(10);

        when(wedstrijdService.findBySportId(sportId)).thenReturn(Arrays.asList(wedstrijd1));
        when(sportService.findById(sportId)).thenReturn(sport);

        mockMvc.perform(get("/wedstrijden/{sportId}", sportId))
               .andExpect(status().isOk())
               .andExpect(view().name("wedstrijden-detail"))
               .andExpect(model().attributeExists("wedstrijden"))
               .andExpect(model().attributeExists("sport"))
               .andExpect(model().attribute("wedstrijden", Arrays.asList(wedstrijd1)))
               .andExpect(model().attribute("sport", sport))
               .andExpect(model().attribute("isAdmin", false));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetWedstrijdWithAdminRole() throws Exception {
        Long sportId = 1L;

        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        Stadium stadium = new Stadium();
        stadium.setId(1L);
        stadium.setNaam("Olympisch Stadion");

        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        wedstrijd1.setSport(sport);
        wedstrijd1.setStadium(stadium);
        wedstrijd1.setDatumTijd(LocalDateTime.now());
        wedstrijd1.setPrijsPerTicket(50.0);
        wedstrijd1.setVrijePlaatsen(0);

        when(wedstrijdService.findBySportId(sportId)).thenReturn(Arrays.asList(wedstrijd1));
        when(sportService.findById(sportId)).thenReturn(sport);

        mockMvc.perform(get("/wedstrijden/{sportId}", sportId))
               .andExpect(status().isOk())
               .andExpect(view().name("wedstrijden-detail"))
               .andExpect(model().attributeExists("wedstrijden"))
               .andExpect(model().attributeExists("sport"))
               .andExpect(model().attribute("wedstrijden", Arrays.asList(wedstrijd1)))
               .andExpect(model().attribute("sport", sport))
               .andExpect(model().attribute("isAdmin", true));

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetWedstrijdWithUserRoleNoTickets() throws Exception {
        Long sportId = 1L;

        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        Stadium stadium = new Stadium();
        stadium.setId(1L);
        stadium.setNaam("Olympisch Stadion");

        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        wedstrijd1.setSport(sport);
        wedstrijd1.setStadium(stadium);
        wedstrijd1.setDatumTijd(LocalDateTime.now());
        wedstrijd1.setPrijsPerTicket(50.0);
        wedstrijd1.setVrijePlaatsen(0);

        when(wedstrijdService.findBySportId(sportId)).thenReturn(Arrays.asList(wedstrijd1));
        when(sportService.findById(sportId)).thenReturn(sport);

        mockMvc.perform(get("/wedstrijden/{sportId}", sportId))
               .andExpect(status().isOk())
               .andExpect(view().name("wedstrijden-detail"))
               .andExpect(model().attributeExists("wedstrijden"))
               .andExpect(model().attributeExists("sport"))
               .andExpect(model().attribute("wedstrijden", Arrays.asList(wedstrijd1)))
               .andExpect(model().attribute("sport", sport))
               .andExpect(model().attribute("isAdmin", false));

    }

    @Test
    @WithAnonymousUser
    public void testNoAccessAnonymous() throws Exception {
        mockMvc.perform(get("/wedstrijden"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }
}
