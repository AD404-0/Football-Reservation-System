package com.Ticket.reservation.Ticket.reservation.repository;

import com.Ticket.reservation.Ticket.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMatchId(Long matchId);
    List<Reservation> findByMatch_IdAndReserved(Long matchId, boolean reserved);
}
