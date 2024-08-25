package com.Ticket.reservation.Ticket.reservation.repository;

import com.Ticket.reservation.Ticket.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository(value = "userRepository")
// This will be used to communicate with the database to perform CRUD operations on User entity.
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
