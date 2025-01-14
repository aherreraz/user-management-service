package com.aherreraz.usermanagement.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("ErrorMessage tests")
class ErrorMessageTest {
    @Test
    @DisplayName("Should create an ErrorMessage with default values")
    void shouldCreateUser() {
        ErrorMessage errorMessage = new ErrorMessage(404, "Error not found");

        assertAll(
                () -> assertInstanceOf(LocalDateTime.class, errorMessage.getTimestamp()),
                () -> assertEquals(404, errorMessage.getCode()),
                () -> assertEquals("Error not found", errorMessage.getDetail())
        );
    }
}