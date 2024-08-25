package com.Ticket.reservation.Ticket.reservation.IntegrationTests;

import com.Ticket.reservation.Ticket.reservation.AbstractTestContainer;
import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import com.Ticket.reservation.Ticket.reservation.repository.MatchRepository;
import com.Ticket.reservation.Ticket.reservation.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class TicketControllerIT extends AbstractTestContainer {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Ticket testTicket;
    private Match testMatch;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
        matchRepository.deleteAll();

        testMatch = new Match();
        testMatch.setHomeTeam("Home Team");
        testMatch.setAwayTeam("Away Team");
        testMatch.setMatchDateTime(new Date());
        testMatch.setVenue("Test Venue");
        testMatch.setTotalCapacity(1000);
        testMatch = matchRepository.save(testMatch);

        testTicket = new Ticket();
        testTicket.setMatch(testMatch);
        testTicket.setPrice(new BigDecimal("50.00"));
        testTicket.setReserved(false);
        testTicket = ticketRepository.save(testTicket);
    }

    @AfterEach
    void tearDown() {
        ticketRepository.deleteAll();
        matchRepository.deleteAll();
    }

    @Test
    void testCreateTicket() throws Exception {
        Match match = new Match();
        match.setHomeTeam("New Home Team");
        match.setAwayTeam("New Away Team");
        match.setMatchDateTime(new Date());
        match.setVenue("New Test Venue");
        match.setTotalCapacity(1000);
        match = matchRepository.save(match);

        Ticket newTicket = new Ticket();
        newTicket.setMatch(match);
        newTicket.setPrice(new BigDecimal("75.00"));
        newTicket.setReserved(false);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTicket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.match.id").value(match.getId()))
                .andExpect(jsonPath("$.price").value(75.00))
                .andExpect(jsonPath("$.reserved").value(false));
    }

    @Test
void testGetTicketsForMatch() throws Exception {
    mockMvc.perform(get("/tickets/match/{matchId}", testMatch.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].match.id").value(testMatch.getId()))
            .andExpect(jsonPath("$[0].price").value(50.00));
}

    @Test
void testGetAvailableTicketsForMatch() throws Exception {
    mockMvc.perform(get("/tickets/match/{matchId}/available", testMatch.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].match.id").value(testMatch.getId()))
            .andExpect(jsonPath("$[0].price").value(50.00))
            .andExpect(jsonPath("$[0].reserved").value(false));
}

    @Test
    void testGetTicketById() throws Exception {
        mockMvc.perform(get("/tickets/{ticketId}", testTicket.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testTicket.getId()))
                .andExpect(jsonPath("$.match.id").value(testMatch.getId()));
    }

    @Test
void testReserveTicket() throws Exception {
    mockMvc.perform(post("/tickets/{ticketId}/reserve", testTicket.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(testTicket.getId()))
            .andExpect(jsonPath("$.reserved").value(true));

    Optional<Ticket> reservedTicket = ticketRepository.findById(testTicket.getId());
    assertThat(reservedTicket).isPresent();
    assertThat(reservedTicket.get().isReserved()).isTrue();
}

    @Test
    void testReserveNonExistentTicket() throws Exception {
        mockMvc.perform(post("/tickets/{ticketId}/reserve", 999L))
                .andExpect(status().isBadRequest());
    }
}