package com.aherreraz.usermanagement.rest.controller;

import com.aherreraz.usermanagement.dto.PhoneDto;
import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("AuthController tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AuthControllerTest {
    private final ObjectMapper objectMapper;
    @LocalServerPort
    private Integer serverPort;

    @BeforeEach
    public void beforeEach() {
        RestAssured.port = serverPort;
        RestAssured.baseURI = "http://localhost";
    }

    @Nested
    @DisplayName("Sign Up")
    class SignUpTest {
        @Test
        @DisplayName("Should sign up a new user with only required fields")
        void success() throws JsonProcessingException {
            SignUpRequestDto requestDto = SignUpRequestDto.builder()
                    .email("andres@example.com")
                    .password("A12hello")
                    .build();

            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(requestDto))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(200);

            assertThat(objectMapper.convertValue(validatableResponse.extract().jsonPath().get(""), SignUpResponseDto.class))
                    .satisfies(actual -> {
                        assertThat(actual.getId()).isInstanceOf(UUID.class);
                        assertThat(actual.getCreated()).isInstanceOf(LocalDateTime.class);
                        assertThat(actual.getLastLogin()).isNull();
                        assertThat(actual.getToken()).isNotBlank();
                        assertThat(actual.getIsActive()).isTrue();
                    });
        }

        @Test
        @DisplayName("Should sign up a new user with optional fields")
        void success_withOptionalFields() throws JsonProcessingException {
            SignUpRequestDto requestDto = SignUpRequestDto.builder()
                    .email("andres@example.com")
                    .password("A12hello")
                    .name("Andres")
                    .phones(List.of(PhoneDto.builder()
                            .countryCode("51")
                            .cityCode(1)
                            .number(12345678L)
                            .build()))
                    .build();
            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(requestDto))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(200);

            assertThat(objectMapper.convertValue(validatableResponse.extract().jsonPath().get(""), SignUpResponseDto.class))
                    .satisfies(actual -> {
                        assertThat(actual.getId()).isInstanceOf(UUID.class);
                        assertThat(actual.getCreated()).isInstanceOf(LocalDateTime.class);
                        assertThat(actual.getLastLogin()).isNull();
                        assertThat(actual.getToken()).isNotBlank();
                        assertThat(actual.getIsActive()).isTrue();
                    });
        }
    }
}