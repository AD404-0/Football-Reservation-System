package com.Ticket.reservation.Ticket.reservation.service;

import com.Ticket.reservation.Ticket.reservation.model.User;
import com.Ticket.reservation.Ticket.reservation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User("testuser", "password", "test@example.com", "Test User", new Date());
    }

    @Test
    void registerUser_ValidUser_ReturnsRegisteredUser() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        User registeredUser = userService.registerUser(sampleUser);

        assertNotNull(registeredUser);
        assertEquals(sampleUser, registeredUser);
        verify(userRepository).save(sampleUser);
    }

    @Test
    void registerUser_ExistingUsername_ThrowsRuntimeException() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(sampleUser));
    }

    @Test
    void findUserByUsername_ValidUsername_ReturnsUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.findUserByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(sampleUser, result.get());
    }

    @Test
    void findUserByUsername_NullUsername_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.findUserByUsername(null));
    }

    @Test
    void getAllUsers_UsersExist_ReturnsListOfUsers() {
        List<User> users = Arrays.asList(sampleUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertFalse(result.isEmpty());
        assertEquals(users, result);
    }

    @Test
    void getAllUsers_NoUsers_ThrowsRuntimeException() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        assertThrows(RuntimeException.class, () -> userService.getAllUsers());
    }

    @Test
    void deleteUser_ValidId_DeletesUser() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(id));
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUser_InvalidId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(0L));
    }

    @Test
    void findUserByEmail_ValidEmail_ReturnsUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.findUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(sampleUser, result.get());
    }

    @Test
    void findUserByEmail_NullEmail_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.findUserByEmail(null));
    }
}