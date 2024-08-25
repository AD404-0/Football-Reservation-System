package com.Ticket.reservation.Ticket.reservation.controller;

import com.Ticket.reservation.Ticket.reservation.model.Reservation;
import com.Ticket.reservation.Ticket.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReservation_ShouldReturnCreatedReservation() {
        Reservation reservation = new Reservation();
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.createReservation(reservation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void getReservationsForUser_ShouldReturnListOfReservations() {
        Long userId = 1L;
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.getReservationsForUser(userId)).thenReturn(reservations);

        ResponseEntity<List<Reservation>> response = reservationController.getReservationsForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservations, response.getBody());
    }

    @Test
    void getReservationsForMatch_ShouldReturnListOfReservations() {
        Long matchId = 1L;
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.getReservationsForMatch(matchId)).thenReturn(reservations);

        ResponseEntity<List<Reservation>> response = reservationController.getReservationsForMatch(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservations, response.getBody());
    }

    @Test
    void getReservationById_ExistingReservation_ShouldReturnReservation() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));

        ResponseEntity<Reservation> response = reservationController.getReservationById(reservationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void getReservationById_NonExistingReservation_ShouldReturnNotFound() {
        Long reservationId = 1L;
        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.empty());

        ResponseEntity<Reservation> response = reservationController.getReservationById(reservationId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void cancelReservation_SuccessfulCancellation_ShouldReturnNoContent() {
        Long reservationId = 1L;
        doNothing().when(reservationService).cancelReservation(reservationId);

        ResponseEntity<Void> response = reservationController.cancelReservation(reservationId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void cancelReservation_FailedCancellation_ShouldReturnBadRequest() {
        Long reservationId = 1L;
        doThrow(new RuntimeException()).when(reservationService).cancelReservation(reservationId);

        ResponseEntity<Void> response = reservationController.cancelReservation(reservationId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}