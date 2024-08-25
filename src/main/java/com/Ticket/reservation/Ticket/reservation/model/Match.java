package com.Ticket.reservation.Ticket.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String homeTeam;

    @NotBlank
    private String awayTeam;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date matchDateTime;

    @NotBlank
    private String venue;

    @Positive
    private int totalCapacity;

    @OneToMany(mappedBy = "match")
    private List<Ticket> tickets;

    // Default constructor
    public Match() {}

    // Parameterized constructor
    public Match(String homeTeam, String awayTeam, Date matchDateTime, String venue, int totalCapacity) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDateTime = matchDateTime;
        this.venue = venue;
        this.totalCapacity = totalCapacity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters and Setters
    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Date getMatchDateTime() {
        return matchDateTime;
    }

    public void setMatchDateTime(Date matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}