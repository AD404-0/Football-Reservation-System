package com.Ticket.reservation.Ticket.reservation.repository;

import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByMatchId(Long matchId);
    List<Ticket> findByMatchIdAndIsReserved(Long matchId, boolean isReserved);
}