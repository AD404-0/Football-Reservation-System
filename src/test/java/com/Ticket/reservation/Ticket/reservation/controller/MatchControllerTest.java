package com.Ticket.reservation.Ticket.reservation.controller;

import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMatch_ValidMatch_ReturnsCreated() {
        Match match = new Match();
        when(matchService.createMatch(any(Match.class))).thenReturn(match);

        ResponseEntity<Match> response = matchController.createMatch(match);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(match, response.getBody());
    }

    @Test
    void createMatch_InvalidMatch_ReturnsBadRequest() {
        Match match = new Match();
        when(matchService.createMatch(any(Match.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Match> response = matchController.createMatch(match);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAllMatches_MatchesExist_ReturnsMatches() {
        List<Match> matches = Arrays.asList(new Match(), new Match());
        when(matchService.getAllMatches()).thenReturn(matches);

        ResponseEntity<List<Match>> response = matchController.getAllMatches();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matches, response.getBody());
    }

    @Test
    void getAllMatches_NoMatches_ReturnsNoContent() {
        when(matchService.getAllMatches()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Match>> response = matchController.getAllMatches();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getMatchById_ValidId_ReturnsMatch() {
        Long matchId = 1L;
        Match match = new Match();
        when(matchService.getMatchById(matchId)).thenReturn(match);

        ResponseEntity<Match> response = matchController.getMatchById(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(match, response.getBody());
    }

    @Test
    void getMatchById_InvalidId_ReturnsBadRequest() {
        ResponseEntity<Match> response = matchController.getMatchById(0L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getMatchById_NonExistentId_ReturnsNotFound() {
        Long matchId = 1L;
        when(matchService.getMatchById(matchId)).thenReturn(null);

        ResponseEntity<Match> response = matchController.getMatchById(matchId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteMatch_ValidId_ReturnsNoContent() {
        Long matchId = 1L;
        doNothing().when(matchService).deleteMatch(matchId);

        ResponseEntity<Void> response = matchController.deleteMatch(matchId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteMatch_InvalidId_ReturnsBadRequest() {
        ResponseEntity<Void> response = matchController.deleteMatch(0L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}