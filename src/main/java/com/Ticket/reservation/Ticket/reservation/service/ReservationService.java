package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.Reservation;
import com.Ticket.reservation.Ticket.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        if (reservation.getUser() == null || reservation.getTicket() == null) {
            throw new IllegalArgumentException("Reservation must have a user and a ticket");
        }
        reservation.setStatus("CONFIRMED");
        reservation.setReservationDateTime(new Date());
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsForUser(Long userId) {
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return reservationRepository.findByMatchId(userId);
    }

    public List<Reservation> getReservationsForMatch(Long matchId) {
        if (matchId == null || matchId < 0) {
            throw new IllegalArgumentException("Invalid match ID");
        }
        return reservationRepository.findByMatch_IdAndReserved(matchId, true);
    }

    public Optional<Reservation> getReservationById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid reservation ID");
        }
        return reservationRepository.findById(id);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus("CANCELLED");
        reservationRepository.save(reservation);
    }
}

