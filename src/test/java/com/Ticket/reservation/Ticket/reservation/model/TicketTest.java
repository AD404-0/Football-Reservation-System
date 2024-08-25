package com.Ticket.reservation.Ticket.reservation.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private Ticket ticket;
    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match(); // Assuming Match has a no-arg constructor
        ticket = new Ticket(match, "A1", new BigDecimal("100.00"), false);
    }

    @Test
    void testTicketConstructorAndGetters() {
        assertEquals(match, ticket.getMatch());
        assertEquals("A1", ticket.getSeatNumber());
        assertEquals(new BigDecimal("100.00"), ticket.getPrice());
        assertFalse(ticket.isReserved());
    }

    @Test
    void testSetters() {
        Match newMatch = new Match();
        ticket.setMatch(newMatch);
        ticket.setSeatNumber("B2");
        ticket.setPrice(new BigDecimal("150.00"));
        ticket.setReserved(true);

        assertEquals(newMatch, ticket.getMatch());
        assertEquals("B2", ticket.getSeatNumber());
        assertEquals(new BigDecimal("150.00"), ticket.getPrice());
        assertTrue(ticket.isReserved());
    }

    @Test
    void testReservation() {
        Reservation reservation = new Reservation();
        ticket.setReservation(reservation);
        assertEquals(reservation, ticket.getReservation());
    }

    @Test
    void testIdSetter() {
        ticket.setId(1L);
        assertEquals(1L, ticket.getId());
    }
}