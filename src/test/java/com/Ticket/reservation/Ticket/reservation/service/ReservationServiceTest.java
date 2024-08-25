package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.Reservation;
import com.Ticket.reservation.Ticket.reservation.model.User;
import com.Ticket.reservation.Ticket.reservation.model.Ticket;
import com.Ticket.reservation.Ticket.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation sampleReservation;
    private User sampleUser;
    private Ticket sampleTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleTicket = new Ticket();
        sampleTicket.setId(1L);
        sampleReservation = new Reservation();
        sampleReservation.setId(1L);
        sampleReservation.setUser(sampleUser);
        sampleReservation.setTicket(sampleTicket);
    }

    @Test
    void createReservation_ValidReservation_ReturnsCreatedReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(sampleReservation);

        Reservation createdReservation = reservationService.createReservation(sampleReservation);

        assertNotNull(createdReservation);
        assertEquals("CONFIRMED", createdReservation.getStatus());
        assertNotNull(createdReservation.getReservationDateTime());
        verify(reservationRepository).save(sampleReservation);
    }

    @Test
    void createReservation_NullReservation_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(null));
    }

    @Test
    void getReservationsForUser_ValidUserId_ReturnsListOfReservations() {
        List<Reservation> reservations = Arrays.asList(sampleReservation);
        when(reservationRepository.findByMatchId(anyLong())).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsForUser(1L);

        assertFalse(result.isEmpty());
        assertEquals(reservations, result);
    }

    @Test
    void getReservationsForMatch_ValidMatchId_ReturnsListOfReservations() {
        List<Reservation> reservations = Arrays.asList(sampleReservation);
        when(reservationRepository.findByMatch_IdAndReserved(anyLong(), eq(true))).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsForMatch(1L);

        assertFalse(result.isEmpty());
        assertEquals(reservations, result);
    }

    @Test
    void getReservationById_ValidId_ReturnsOptionalReservation() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(sampleReservation));

        Optional<Reservation> result = reservationService.getReservationById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleReservation, result.get());
    }

    @Test
    void cancelReservation_ValidReservationId_UpdatesReservationStatus() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(sampleReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(sampleReservation);

        reservationService.cancelReservation(1L);

        assertEquals("CANCELLED", sampleReservation.getStatus());
        verify(reservationRepository).save(sampleReservation);
    }

    @Test
    void cancelReservation_NonExistentReservationId_ThrowsRuntimeException() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.cancelReservation(1L));
    }
}