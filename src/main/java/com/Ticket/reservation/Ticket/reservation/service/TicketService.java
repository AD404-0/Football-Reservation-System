package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import com.Ticket.reservation.Ticket.reservation.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        if (ticket.getMatch() == null || ticket.getPrice() == null) {
            throw new IllegalArgumentException("Match ID and price are required");
        }
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsForMatch(Long matchId) {
        if (matchId == null || matchId <= 0) {
            throw new IllegalArgumentException("Invalid match ID");
        }
        List<Ticket> tickets = ticketRepository.findByMatchId(matchId);
        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets found for match ID: " + matchId);
        }
        return tickets;
    }

    public List<Ticket> getAvailableTicketsForMatch(Long matchId) {
        if (matchId == null || matchId <= 0) {
            throw new IllegalArgumentException("Invalid match ID");
        }
        List<Ticket> availableTickets = ticketRepository.findByMatchIdAndIsReserved(matchId, false);
        if (availableTickets.isEmpty()) {
            throw new RuntimeException("No available tickets found for match ID: " + matchId);
        }
        return availableTickets;
    }

    public Ticket getTicketById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }

    public Ticket reserveTicket(Long ticketId) {
        if (ticketId == null || ticketId <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID");
        }
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));
        if (ticket.isReserved()) {
            throw new IllegalStateException("Ticket is already reserved");
        }
        ticket.setReserved(true);
        return ticketRepository.save(ticket);
    }
}
