package com.aherreraz.usermanagement.model;

import com.aherreraz.usermanagement.port.PasswordEncoderPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("User tests")
class UserTest {
    @Test
    @DisplayName("Should create a User with default values")
    void shouldCreateUser() {
        User newUser = new User();

        assertAll(
                () -> assertInstanceOf(UUID.class, newUser.getId()),
                () -> assertInstanceOf(LocalDateTime.class, newUser.getCreated()),
                () -> assertNull(newUser.getLastLogin()),
                () -> assertEquals(true, newUser.getIsActive()),
                () -> assertNull(newUser.getEmail()),
                () -> assertNull(newUser.getName()),
                () -> assertNull(newUser.getPassword()),
                () -> assertNull(newUser.getPhones())
        );
    }

    @Test
    @DisplayName("Should encode password using password encoder")
    void shouldEncodePassword() {
        PasswordEncoderPort passwordEncoder = mock(PasswordEncoderPort.class);
        when(passwordEncoder.encode("cleanPassword")).thenReturn("encodedPassword");

        User newUser = new User();
        newUser.setCleanPassword("cleanPassword");
        newUser.encodePassword(passwordEncoder);

        assertEquals(newUser.getPassword(), "encodedPassword");
    }
}