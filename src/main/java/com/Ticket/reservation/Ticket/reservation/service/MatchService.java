package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match createMatch(Match match) {
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        if (match.getMatchDateTime() == null || match.getMatchDateTime().before(new Date())) {
            throw new IllegalArgumentException("Match date must be in the future");
        }
        return matchRepository.save(match);
    }

    public List<Match> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        if (matches.isEmpty()) {
            throw new RuntimeException("No matches found");
        }
        return matches;
    }

    public List<Match> getFutureMatches() {
        List<Match> futureMatches = matchRepository.findByMatchDateTimeAfter(new Date());
        if (futureMatches.isEmpty()) {
            throw new RuntimeException("No future matches found");
        }
        return futureMatches;
    }

    public List<Match> getMatchesByTeam(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        List<Match> teamMatches = matchRepository.findByHomeTeamOrAwayTeam(teamName, teamName);
        if (teamMatches.isEmpty()) {
            throw new RuntimeException("No matches found for team: " + teamName);
        }
        return teamMatches;
    }

    public Match getMatchById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid match ID");
        }
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with ID: " + id));
    }

    public void deleteMatch(Long matchId) {
        if (matchId == null || matchId <= 0) {
            throw new IllegalArgumentException("Invalid match ID");
        }
        if (!matchRepository.existsById(matchId)) {
            throw new RuntimeException("Match not found with ID: " + matchId);
        }
        matchRepository.deleteById(matchId);
    }

}
