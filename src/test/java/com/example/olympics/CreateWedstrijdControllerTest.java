package com.example.olympics;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.Sport;
import domain.Stadium;
import domain.Wedstrijd;
import service.SportService;
import service.StadiumService;
import service.WedstrijdService;
import validator.DateTimeValidator;
import validator.OlympicNumber1Validator;
import validator.OlympicNumber2Validator;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateWedstrijdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportService sportService;

    @MockBean
    private StadiumService stadiumService;

    @MockBean
    private WedstrijdService wedstrijdService;

    @MockBean
    private DateTimeValidator dateTimeValidator;

    @MockBean
    private OlympicNumber1Validator olympicNumber1Validator;

    @MockBean
    private OlympicNumber2Validator olympicNumber2Validator;

    @InjectMocks
    private CreateWedstrijdController createWedstrijdController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(createWedstrijdController, "sportService", sportService);
        ReflectionTestUtils.setField(createWedstrijdController, "stadiumService", stadiumService);
        ReflectionTestUtils.setField(createWedstrijdController, "wedstrijdService", wedstrijdService);
        ReflectionTestUtils.setField(createWedstrijdController, "dateTimeValidator", dateTimeValidator);
        ReflectionTestUtils.setField(createWedstrijdController, "olympicNumber1Validator", olympicNumber1Validator);
        ReflectionTestUtils.setField(createWedstrijdController, "olympicNumber2Validator", olympicNumber2Validator);
    }

    private static Stream<Arguments> validWedstrijdData() {
        return Stream.of(
            Arguments.of("2024-07-28T18:00", "12345", "12346", "50.0", "20", "1"),
            Arguments.of("2024-08-01T14:00", "54321", "54322", "60.0", "10", "2")
        );
    }

    private static Stream<Arguments> invalidWedstrijdData() {
        return Stream.of(
            Arguments.of("", "12345", "12346", "50.0", "20", "1"),
            Arguments.of("2024-07-28T18:00", "", "12346", "50.0", "20", "1"),
            Arguments.of("2024-07-28T18:00", "12345", "", "50.0", "20", "1"),
            Arguments.of("2024-07-28T18:00", "12345", "12346", "", "20", "1"),
            Arguments.of("2024-07-28T18:00", "12345", "12346", "50.0", "", "1")
        );
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowCreateForm() throws Exception {
        Long sportId = 1L;
        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        when(sportService.findById(sportId)).thenReturn(sport);
        when(stadiumService.findAll()).thenReturn(Collections.emptyList());
        when(wedstrijdService.findDisciplinesBySportId(sportId)).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/wedstrijden/{sportId}/create", sportId))
               .andExpect(status().isOk())
               .andExpect(view().name("create-wedstrijd"))
               .andExpect(model().attributeExists("wedstrijd"))
               .andExpect(model().attributeExists("stadiums"))
               .andExpect(model().attributeExists("disciplines"))
               .andExpect(model().attributeExists("sport"))
               .andExpect(model().attribute("sport", sport));
    }

    @ParameterizedTest
    @MethodSource("validWedstrijdData")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateWedstrijd_Success(String datumTijd, String olympicNumber1, String olympicNumber2, String prijsPerTicket, String vrijePlaatsen, String stadiumId) throws Exception {
        Long sportId = 1L;
        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        Stadium stadium = new Stadium();
        stadium.setId(Long.parseLong(stadiumId));
        stadium.setNaam("Olympisch Stadion");

        when(sportService.findById(sportId)).thenReturn(sport);
        when(stadiumService.findById(Long.parseLong(stadiumId))).thenReturn(stadium);

        mockMvc.perform(post("/wedstrijden/{sportId}/create", sportId)
               .param("datumTijd", datumTijd)
               .param("olympicNumber1", olympicNumber1)
               .param("olympicNumber2", olympicNumber2)
               .param("prijsPerTicket", prijsPerTicket)
               .param("vrijePlaatsen", vrijePlaatsen)
               .param("stadium.id", stadiumId)
               .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/sport"));

        verify(wedstrijdService).save(Mockito.any(Wedstrijd.class));
    }

    @ParameterizedTest
    @MethodSource("invalidWedstrijdData")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateWedstrijd_Failure(String datumTijd, String olympicNumber1, String olympicNumber2, String prijsPerTicket, String vrijePlaatsen, String stadiumId) throws Exception {
        Long sportId = 1L;
        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        when(sportService.findById(sportId)).thenReturn(sport);
        when(stadiumService.findAll()).thenReturn(Collections.emptyList());
        when(wedstrijdService.findDisciplinesBySportId(sportId)).thenReturn(Collections.emptySet());

        mockMvc.perform(post("/wedstrijden/{sportId}/create", sportId)
               .param("datumTijd", datumTijd)
               .param("olympicNumber1", olympicNumber1)
               .param("olympicNumber2", olympicNumber2)
               .param("prijsPerTicket", prijsPerTicket)
               .param("vrijePlaatsen", vrijePlaatsen)
               .param("stadium.id", stadiumId)
               .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("create-wedstrijd"))
               .andExpect(model().attributeHasErrors("wedstrijd"))
               .andExpect(model().attributeExists("stadiums"))
               .andExpect(model().attributeExists("sport"))
               .andExpect(model().attributeExists("disciplines"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowCreateFormAsUser() throws Exception {
        Long sportId = 1L;
        Sport sport = new Sport();
        sport.setId(sportId);
        sport.setNaam("Atletiek");

        when(sportService.findById(sportId)).thenReturn(sport);
        when(stadiumService.findAll()).thenReturn(Collections.emptyList());
        when(wedstrijdService.findDisciplinesBySportId(sportId)).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/wedstrijden/{sportId}/create", sportId))
        .andExpect(status().isForbidden());
    }
}
