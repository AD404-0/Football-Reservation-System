package com.Ticket.reservation.Ticket.reservation.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private Match match;
    private Date matchDateTime;

    @BeforeEach
    void setUp() {
        matchDateTime = new Date();
        match = new Match("Home Team", "Away Team", matchDateTime, "Stadium", 1000);
    }

    @Test
    void testMatchConstructorAndGetters() {
        assertEquals("Home Team", match.getHomeTeam());
        assertEquals("Away Team", match.getAwayTeam());
        assertEquals(matchDateTime, match.getMatchDateTime());
        assertEquals("Stadium", match.getVenue());
        assertEquals(1000, match.getTotalCapacity());
    }

    @Test
    void testSetters() {
        match.setId(1L);
        match.setHomeTeam("New Home Team");
        match.setAwayTeam("New Away Team");
        Date newDateTime = new Date();
        match.setMatchDateTime(newDateTime);
        match.setVenue("New Stadium");
        match.setTotalCapacity(2000);
        match.setTickets(new ArrayList<>());

        assertEquals(1L, match.getId());
        assertEquals("New Home Team", match.getHomeTeam());
        assertEquals("New Away Team", match.getAwayTeam());
        assertEquals(newDateTime, match.getMatchDateTime());
        assertEquals("New Stadium", match.getVenue());
        assertEquals(2000, match.getTotalCapacity());
        assertNotNull(match.getTickets());
        assertTrue(match.getTickets().isEmpty());
    }
}