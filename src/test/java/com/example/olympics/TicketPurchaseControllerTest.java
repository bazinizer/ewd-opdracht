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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.MyUser;
import domain.Sport;
import domain.Stadium;
import domain.Ticket;
import domain.Wedstrijd;
import service.MyUserService;
import service.TicketService;
import service.WedstrijdService;
import validator.TicketPurchaseValidator;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketPurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private WedstrijdService wedstrijdService;

    @MockBean
    private MyUserService myUserService;

    @MockBean
    private TicketPurchaseValidator purchaseValidator;

    @InjectMocks
    private TicketPurchaseController ticketPurchaseController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(ticketPurchaseController, "ticketService", ticketService);
        ReflectionTestUtils.setField(ticketPurchaseController, "wedstrijdService", wedstrijdService);
        ReflectionTestUtils.setField(ticketPurchaseController, "myUserService", myUserService);
        ReflectionTestUtils.setField(ticketPurchaseController, "purchaseValidator", purchaseValidator);
    }

    private static Stream<Arguments> validTicketData() {
        return Stream.of(
            Arguments.of("2"),
            Arguments.of("5")
        );
    }

    private static Stream<Arguments> invalidTicketData() {
        return Stream.of(
            Arguments.of("0"),
            Arguments.of("21")
        );
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowTicketPurchase() throws Exception {
        Long wedstrijdId = 1L;
        Long userId = 1L;

        Wedstrijd wedstrijd = new Wedstrijd();
        wedstrijd.setId(wedstrijdId);
        wedstrijd.setSport(new Sport("Atletiek", Collections.emptySet()));
        wedstrijd.setStadium(new Stadium(1L, "Olympisch Stadion"));
        wedstrijd.setDatumTijd(LocalDateTime.of(2024, 7, 28, 18, 0));
        wedstrijd.setPrijsPerTicket(50.0);
        wedstrijd.setVrijePlaatsen(20);

        when(wedstrijdService.findById(wedstrijdId)).thenReturn(wedstrijd);
        when(myUserService.getUserIdByUsername("user")).thenReturn(userId);
        when(ticketService.getTicketsBoughtForWedstrijdByUser(wedstrijdId, userId)).thenReturn(0);

        mockMvc.perform(get("/wedstrijden/{wedstrijdId}/koopTicket", wedstrijdId))
               .andExpect(status().isOk())
               .andExpect(view().name("koopTicket"))
               .andExpect(model().attributeExists("wedstrijd"))
               .andExpect(model().attributeExists("ticketsAlreadyBought"))
               .andExpect(model().attributeExists("remainingTickets"))
               .andExpect(model().attributeExists("ticket"))
               .andExpect(model().attributeExists("formattedDate"))
               .andExpect(model().attribute("wedstrijd", wedstrijd));
    }

    @ParameterizedTest
    @MethodSource("validTicketData")
    @WithMockUser(username = "user", roles = {"USER"})
    public void testBuyTickets_Success(String aantal) throws Exception {
        Long wedstrijdId = 1L;
        Long userId = 1L;

        Wedstrijd wedstrijd = new Wedstrijd();
        wedstrijd.setId(wedstrijdId);
        wedstrijd.setSport(new Sport("Atletiek", Collections.emptySet()));
        wedstrijd.setStadium(new Stadium(1L, "Olympisch Stadion"));
        wedstrijd.setDatumTijd(LocalDateTime.of(2024, 7, 28, 18, 0));
        wedstrijd.setPrijsPerTicket(50.0);
        wedstrijd.setVrijePlaatsen(20);

        MyUser user = new MyUser();
        user.setId(userId);
        user.setUsername("user");

        when(wedstrijdService.findById(wedstrijdId)).thenReturn(wedstrijd);
        when(myUserService.getUserIdByUsername("user")).thenReturn(userId);
        when(myUserService.findByUsername("user")).thenReturn(user);

        mockMvc.perform(post("/wedstrijden/{wedstrijdId}/koopTicket", wedstrijdId)
               .param("aantal", aantal)
               .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/sport"));

        verify(ticketService).purchaseTickets(userId, wedstrijdId, Integer.parseInt(aantal));
    }

    @ParameterizedTest
    @MethodSource("invalidTicketData")
    @WithMockUser(username = "user", roles = {"USER"})
    public void testBuyTickets_Failure(String aantal) throws Exception {
        Long wedstrijdId = 1L;
        Long userId = 1L;

        Wedstrijd wedstrijd = new Wedstrijd();
        wedstrijd.setId(wedstrijdId);
        wedstrijd.setSport(new Sport("Atletiek", Collections.emptySet()));
        wedstrijd.setStadium(new Stadium(1L, "Olympisch Stadion"));
        wedstrijd.setDatumTijd(LocalDateTime.of(2024, 7, 28, 18, 0));
        wedstrijd.setPrijsPerTicket(50.0);
        wedstrijd.setVrijePlaatsen(20);

        MyUser user = new MyUser();
        user.setId(userId);
        user.setUsername("user");

        when(wedstrijdService.findById(wedstrijdId)).thenReturn(wedstrijd);
        when(myUserService.getUserIdByUsername("user")).thenReturn(userId);
        when(myUserService.findByUsername("user")).thenReturn(user);

        mockMvc.perform(post("/wedstrijden/{wedstrijdId}/koopTicket", wedstrijdId)
               .param("aantal", aantal)
               .with(csrf()))
               .andExpect(status().isOk())
               .andExpect(view().name("koopTicket"))
               .andExpect(model().attributeHasFieldErrors("ticket", "aantal"))
               .andExpect(model().attributeExists("wedstrijd"))
               .andExpect(model().attributeExists("ticketsAlreadyBought"))
               .andExpect(model().attributeExists("remainingTickets"))
               .andExpect(model().attributeExists("formattedDate"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowTicketPurchaseAsAdmin() throws Exception {
        Long wedstrijdId = 1L;

        mockMvc.perform(get("/wedstrijden/{wedstrijdId}/koopTicket", wedstrijdId))
               .andExpect(status().isForbidden());
    }
}
