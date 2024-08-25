package com.Ticket.reservation.Ticket.reservation.controller;

import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import com.Ticket.reservation.Ticket.reservation.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket() {
        Ticket ticket = new Ticket();
        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.createTicket(ticket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void getTicketsForMatch() {
        Long matchId = 1L;
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getTicketsForMatch(matchId)).thenReturn(tickets);

        ResponseEntity<List<Ticket>> response = ticketController.getTicketsForMatch(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void getAvailableTicketsForMatch() {
        Long matchId = 1L;
        List<Ticket> availableTickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAvailableTicketsForMatch(matchId)).thenReturn(availableTickets);

        ResponseEntity<List<Ticket>> response = ticketController.getAvailableTicketsForMatch(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availableTickets, response.getBody());
    }

    @Test
    void getTicketById_Found() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        when(ticketService.getTicketById(ticketId)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void getTicketById_NotFound() {
        Long ticketId = 1L;
        when(ticketService.getTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void reserveTicket_Success() {
        Long ticketId = 1L;
        Ticket reservedTicket = new Ticket();
        when(ticketService.reserveTicket(ticketId)).thenReturn(reservedTicket);

        ResponseEntity<Ticket> response = ticketController.reserveTicket(ticketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservedTicket, response.getBody());
    }

    @Test
    void reserveTicket_Failure() {
        Long ticketId = 1L;
        when(ticketService.reserveTicket(ticketId)).thenThrow(new RuntimeException());

        ResponseEntity<Ticket> response = ticketController.reserveTicket(ticketId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}