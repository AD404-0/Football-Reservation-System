package com.Ticket.reservation.Ticket.reservation.controller;

import com.Ticket.reservation.Ticket.reservation.model.Match;
import com.Ticket.reservation.Ticket.reservation.service.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody @Valid Match match) {
        try {
            Match createdMatch = matchService.createMatch(match);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return matches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(matches);
    }

    @GetMapping("/future")
    public ResponseEntity<List<Match>> getFutureMatches() {
        List<Match> futureMatches = matchService.getFutureMatches();
        return futureMatches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(futureMatches);
    }

    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<Match>> getMatchesByTeam(@PathVariable String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Match> teamMatches = matchService.getMatchesByTeam(teamName);
        return teamMatches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(teamMatches);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long matchId) {
        if (matchId == null || matchId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Match match = matchService.getMatchById(matchId);
        return match != null ? ResponseEntity.ok(match) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long matchId) {
        if (matchId == null || matchId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            matchService.deleteMatch(matchId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
