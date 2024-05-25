package com.example.olympics;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.Sport;
import domain.Stadium;
import domain.Wedstrijd;
import exceptions.SportNotFoundException;
import exceptions.WedstrijdNotFoundException;
import service.WedstrijdService;

@SpringBootTest
@AutoConfigureMockMvc
public class WedstrijdRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WedstrijdService wedstrijdService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWedstrijdAvailability() throws Exception {
        Long wedstrijdId = 1L;
        Wedstrijd wedstrijd = new Wedstrijd();
        wedstrijd.setId(wedstrijdId);
        wedstrijd.setSport(new Sport("Atletiek", Collections.emptySet()));
        wedstrijd.setStadium(new Stadium(1L, "Olympisch Stadion"));
        wedstrijd.setDatumTijd(LocalDateTime.of(2024, 7, 28, 18, 0));
        wedstrijd.setPrijsPerTicket(50.0);
        wedstrijd.setVrijePlaatsen(20);

        when(wedstrijdService.findById(wedstrijdId)).thenReturn(wedstrijd);

        mockMvc.perform(get("/rest/wedstrijdAvailability/{id}", wedstrijdId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(wedstrijdId))
               .andExpect(jsonPath("$.vrijePlaatsen").value(20));
    }

    @Test
    public void testGetWedstrijdenBySport() throws Exception {
        Long sportId = 1L;
        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        wedstrijd1.setSport(new Sport("Atletiek", Collections.emptySet()));
        wedstrijd1.setStadium(new Stadium(1L, "Olympisch Stadion"));
        wedstrijd1.setDatumTijd(LocalDateTime.of(2024, 7, 28, 18, 0));
        wedstrijd1.setPrijsPerTicket(50.0);
        wedstrijd1.setVrijePlaatsen(20);

        Wedstrijd wedstrijd2 = new Wedstrijd();
        wedstrijd2.setId(2L);
        wedstrijd2.setSport(new Sport("Atletiek", Collections.emptySet()));
        wedstrijd2.setStadium(new Stadium(1L, "Olympisch Stadion"));
        wedstrijd2.setDatumTijd(LocalDateTime.of(2024, 7, 29, 18, 0));
        wedstrijd2.setPrijsPerTicket(55.0);
        wedstrijd2.setVrijePlaatsen(15);

        List<Wedstrijd> wedstrijden = List.of(wedstrijd1, wedstrijd2);

        when(wedstrijdService.findBySportId(sportId)).thenReturn(wedstrijden);

        mockMvc.perform(get("/rest/sport/{id}", sportId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1L))
               .andExpect(jsonPath("$[0].vrijePlaatsen").value(20))
               .andExpect(jsonPath("$[1].id").value(2L))
               .andExpect(jsonPath("$[1].vrijePlaatsen").value(15));
    }

    @Test
    public void testGetWedstrijdAvailability_NotFound() throws Exception {
        Long wedstrijdId = 50L;

        when(wedstrijdService.findById(wedstrijdId)).thenThrow(new WedstrijdNotFoundException(wedstrijdId));

        mockMvc.perform(get("/rest/wedstrijdAvailability/{id}", wedstrijdId))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$").value("Could not find wedstrijd with id " + wedstrijdId));
    }

    @Test
    public void testGetWedstrijdenBySport_NotFound() throws Exception {
        Long sportId = 10L;

        when(wedstrijdService.findBySportId(sportId)).thenThrow(new SportNotFoundException(sportId));

        mockMvc.perform(get("/rest/sport/{id}", sportId))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$").value("Could not find sport with id " + sportId));
    }
}
