package com.Ticket.reservation.Ticket.reservation.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationTest {

    private Reservation reservation;
    private User user;
    private Match match;
    private Ticket ticket;
    private Date reservationDateTime;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        match = mock(Match.class);
        ticket = mock(Ticket.class);
        reservationDateTime = new Date();
        reservation = new Reservation(user, match, ticket, reservationDateTime, "CONFIRMED", true);
    }

    @Test
    void testReservationConstructorAndGetters() {
        assertEquals(user, reservation.getUser());
        assertEquals(match, reservation.getMatch());
        assertEquals(ticket, reservation.getTicket());
        assertEquals(reservationDateTime, reservation.getReservationDateTime());
        assertEquals("CONFIRMED", reservation.getStatus());
        assertTrue(reservation.isReserved());
    }

    @Test
    void testSetters() {
        User newUser = mock(User.class);
        Match newMatch = mock(Match.class);
        Ticket newTicket = mock(Ticket.class);
        Date newDate = new Date();

        reservation.setUser(newUser);
        reservation.setMatch(newMatch);
        reservation.setTicket(newTicket);
        reservation.setReservationDateTime(newDate);
        reservation.setStatus("CANCELLED");
        reservation.setReserved(false);

        assertEquals(newUser, reservation.getUser());
        assertEquals(newMatch, reservation.getMatch());
        assertEquals(newTicket, reservation.getTicket());
        assertEquals(newDate, reservation.getReservationDateTime());
        assertEquals("CANCELLED", reservation.getStatus());
        assertFalse(reservation.isReserved());
    }

    @Test
    void testIdSetter() {
        reservation.setId(1L);
        assertEquals(1L, reservation.getId());
    }
}