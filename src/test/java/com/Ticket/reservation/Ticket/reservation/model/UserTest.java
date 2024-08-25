package com.Ticket.reservation.Ticket.reservation.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "password123", "testuser@example.com", "Test User", new Date());
    }

    @Test
    void testGetters() {
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("Test User", user.getFullName());
        assertNotNull(user.getDateOfBirth());
    }

    @Test
    void testSetters() {
        user.setUsername("newuser");
        user.setPassword("newpassword");
        user.setEmail("newuser@example.com");
        user.setFullName("New User");
        Date newDate = new Date();
        user.setDateOfBirth(newDate);

        assertEquals("newuser", user.getUsername());
        assertEquals("newpassword", user.getPassword());
        assertEquals("newuser@example.com", user.getEmail());
        assertEquals("New User", user.getFullName());
        assertEquals(newDate, user.getDateOfBirth());
    }

    @Test
    void testReservation() {
        Reservation reservation = new Reservation();
        user.setReservation(reservation);

        assertEquals(reservation, user.getReservation());
    }
}