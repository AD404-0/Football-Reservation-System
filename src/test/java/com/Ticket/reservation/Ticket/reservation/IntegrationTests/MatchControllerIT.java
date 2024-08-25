package com.Ticket.reservation.Ticket.reservation.IntegrationTests;

import com.Ticket.reservation.Ticket.reservation.AbstractTestContainer;
import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.repository.MatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class MatchControllerIT extends AbstractTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchRepository matchRepository;
    // ObjectMapper is used to convert objects to JSON and vice versa
    @Autowired
    private ObjectMapper objectMapper;

    private Match testMatch;

    @BeforeEach
    void setUp() {
        matchRepository.deleteAll();
        testMatch = new Match();
        testMatch.setHomeTeam("Home Team");
        testMatch.setAwayTeam("Away Team");
        testMatch.setMatchDateTime(new Date());
        testMatch = matchRepository.save(testMatch);
    }

    @Test
void testCreateMatch() throws Exception {
    Match newMatch = new Match();
    newMatch.setHomeTeam("New Home Team");
    newMatch.setAwayTeam("New Away Team");
    newMatch.setMatchDateTime(new Date(System.currentTimeMillis() + 86400000)); // Set to tomorrow
    newMatch.setVenue("Test Venue");
    newMatch.setTotalCapacity(1000);

    mockMvc.perform(post("/api/v1/matches")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newMatch)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.homeTeam").value("New Home Team"))
            .andExpect(jsonPath("$.awayTeam").value("New Away Team"))
            .andExpect(jsonPath("$.venue").value("Test Venue"))
            .andExpect(jsonPath("$.totalCapacity").value(1000));
}

    @Test
    void testGetAllMatches() throws Exception {
        mockMvc.perform(get("/api/v1/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].homeTeam").value("Home Team"))
                .andExpect(jsonPath("$[0].awayTeam").value("Away Team"));
    }

    @Test
    void testGetMatchById() throws Exception {
        mockMvc.perform(get("/api/v1/matches/{matchId}", testMatch.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homeTeam").value("Home Team"))
                .andExpect(jsonPath("$.awayTeam").value("Away Team"));
    }

    @Test
void testDeleteMatch() throws Exception {
    mockMvc.perform(delete("/api/v1/matches/{matchId}", testMatch.getId()))
            .andExpect(status().isNoContent());

    assertThat(matchRepository.findById(testMatch.getId())).isEmpty();
}
}