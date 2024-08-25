package com.Ticket.reservation.Ticket.reservation.repository;

import com.Ticket.reservation.Ticket.reservation.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByMatchDateTimeAfter(Date date);
    List<Match> findByHomeTeamOrAwayTeam(String homeTeam, String awayTeam);

}