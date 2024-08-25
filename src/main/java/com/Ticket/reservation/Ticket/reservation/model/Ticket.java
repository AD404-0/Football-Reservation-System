package com.Ticket.reservation.Ticket.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    @NotNull
    private Match match;

    @NotBlank
    private String seatNumber;

    @NotNull
    @Positive
    private BigDecimal price;

    private boolean isReserved;

    @OneToOne(mappedBy = "ticket")
    private Reservation reservation;

    // Default constructor
    public Ticket() {}

    // Parameterized constructor
    public Ticket(Match match, String seatNumber, BigDecimal price, boolean isReserved) {
        this.match = match;
        this.seatNumber = seatNumber;
        this.price = price;
        this.isReserved = isReserved;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


}