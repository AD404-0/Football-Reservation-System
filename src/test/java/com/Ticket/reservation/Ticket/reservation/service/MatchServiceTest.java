package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    private Match sampleMatch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMatch = new Match("Home Team", "Away Team", new Date(System.currentTimeMillis() + 86400000), "Venue", 1000);
    }

    @Test
    void createMatch_ValidMatch_ReturnsCreatedMatch() {
        when(matchRepository.save(any(Match.class))).thenReturn(sampleMatch);

        Match createdMatch = matchService.createMatch(sampleMatch);

        assertNotNull(createdMatch);
        assertEquals(sampleMatch, createdMatch);
        verify(matchRepository).save(sampleMatch);
    }

    @Test
    void createMatch_NullMatch_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> matchService.createMatch(null));
    }

    @Test
    void getAllMatches_MatchesExist_ReturnsListOfMatches() {
        List<Match> matches = Arrays.asList(sampleMatch);
        when(matchRepository.findAll()).thenReturn(matches);

        List<Match> result = matchService.getAllMatches();

        assertFalse(result.isEmpty());
        assertEquals(matches, result);
    }

    @Test
    void getAllMatches_NoMatches_ThrowsRuntimeException() {
        when(matchRepository.findAll()).thenReturn(Arrays.asList());

        assertThrows(RuntimeException.class, () -> matchService.getAllMatches());
    }

    @Test
    void getMatchById_ValidId_ReturnsMatch() {
        Long id = 1L;
        when(matchRepository.findById(id)).thenReturn(Optional.of(sampleMatch));

        Match result = matchService.getMatchById(id);

        assertNotNull(result);
        assertEquals(sampleMatch, result);
    }

    @Test
    void getMatchById_InvalidId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> matchService.getMatchById(0L));
    }

    @Test
    void getMatchById_NonExistentId_ThrowsRuntimeException() {
        Long id = 1L;
        when(matchRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> matchService.getMatchById(id));
    }

    @Test
    void deleteMatch_ValidId_DeletesMatch() {
        Long id = 1L;
        when(matchRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> matchService.deleteMatch(id));
        verify(matchRepository).deleteById(id);
    }

    @Test
    void deleteMatch_InvalidId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> matchService.deleteMatch(0L));
    }

    @Test
    void deleteMatch_NonExistentId_ThrowsRuntimeException() {
        Long id = 1L;
        when(matchRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> matchService.deleteMatch(id));
    }
}