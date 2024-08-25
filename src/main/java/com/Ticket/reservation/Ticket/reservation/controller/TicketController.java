package com.Ticket.reservation.Ticket.reservation.controller;

import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import com.Ticket.reservation.Ticket.reservation.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(createdTicket);
    }

    // This method is used to get all tickets
    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<Ticket>> getTicketsForMatch(@PathVariable Long matchId) {
        List<Ticket> tickets = ticketService.getTicketsForMatch(matchId);
        return ResponseEntity.ok(tickets);
    }

    // This method is used to get all available tickets
    @GetMapping("/match/{matchId}/available")
    public ResponseEntity<List<Ticket>> getAvailableTicketsForMatch(@PathVariable Long matchId) {
        List<Ticket> availableTickets = ticketService.getAvailableTicketsForMatch(matchId);
        return ResponseEntity.ok(availableTickets);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{ticketId}/reserve")
    public ResponseEntity<Ticket> reserveTicket(@PathVariable Long ticketId) {
        try {
            Ticket reservedTicket = ticketService.reserveTicket(ticketId);
            return ResponseEntity.ok(reservedTicket);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
