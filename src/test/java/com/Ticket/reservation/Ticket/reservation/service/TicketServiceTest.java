package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import com.Ticket.reservation.Ticket.reservation.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket sampleTicket;
    private Match sampleMatch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMatch = new Match();
        sampleMatch.setId(1L);
        sampleTicket = new Ticket(sampleMatch, "A1", new BigDecimal("50.00"), false);
        sampleTicket.setId(1L);
    }

    @Test
    void createTicket_ValidTicket_ReturnsCreatedTicket() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(sampleTicket);

        Ticket createdTicket = ticketService.createTicket(sampleTicket);

        assertNotNull(createdTicket);
        assertEquals(sampleTicket, createdTicket);
        verify(ticketRepository).save(sampleTicket);
    }

    @Test
    void createTicket_NullTicket_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ticketService.createTicket(null));
    }

    @Test
    void getTicketsForMatch_ValidMatchId_ReturnsListOfTickets() {
        List<Ticket> tickets = Arrays.asList(sampleTicket);
        when(ticketRepository.findByMatchId(anyLong())).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketsForMatch(1L);

        assertFalse(result.isEmpty());
        assertEquals(tickets, result);
    }

    @Test
    void getTicketsForMatch_InvalidMatchId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketsForMatch(0L));
    }

    @Test
    void getAvailableTicketsForMatch_ValidMatchId_ReturnsListOfAvailableTickets() {
        List<Ticket> availableTickets = Arrays.asList(sampleTicket);
        when(ticketRepository.findByMatchIdAndIsReserved(anyLong(), eq(false))).thenReturn(availableTickets);

        List<Ticket> result = ticketService.getAvailableTicketsForMatch(1L);

        assertFalse(result.isEmpty());
        assertEquals(availableTickets, result);
    }

    @Test
    void getTicketById_ValidId_ReturnsTicket() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(sampleTicket));

        Ticket result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(sampleTicket, result);
    }

    @Test
    void reserveTicket_ValidIdAndAvailableTicket_ReturnsReservedTicket() {
        Ticket availableTicket = new Ticket(sampleMatch, "A2", new BigDecimal("50.00"), false);
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(availableTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(availableTicket);

        Ticket reservedTicket = ticketService.reserveTicket(1L);

        assertTrue(reservedTicket.isReserved());
        verify(ticketRepository).save(availableTicket);
    }

    @Test
    void reserveTicket_AlreadyReservedTicket_ThrowsIllegalStateException() {
        Ticket reservedTicket = new Ticket(sampleMatch, "A3", new BigDecimal("50.00"), true);
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(reservedTicket));

        assertThrows(IllegalStateException.class, () -> ticketService.reserveTicket(1L));
    }
}