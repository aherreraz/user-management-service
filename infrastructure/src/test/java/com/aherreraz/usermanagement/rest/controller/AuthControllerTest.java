package com.aherreraz.usermanagement.rest.controller;

import com.aherreraz.usermanagement.dto.LoginRequestDto;
import com.aherreraz.usermanagement.dto.LoginResponseDto;
import com.aherreraz.usermanagement.dto.PhoneDto;
import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.aherreraz.usermanagement.model.ErrorMessage;
import com.aherreraz.usermanagement.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

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
    private final JwtUtil jwtUtil;
    @LocalServerPort
    private Integer serverPort;

    @DynamicPropertySource
    static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
        String uniqueDbName = "testdb-" + UUID.randomUUID();
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:" + uniqueDbName);
        registry.add("spring.datasource.driver-class-name", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
    }

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
                    .statusCode(HttpStatus.SC_CREATED);

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
                    .statusCode(HttpStatus.SC_CREATED);

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
        @DisplayName("Should return an error if email is invalid")
        void error_emailValidation() throws JsonProcessingException {
            SignUpRequestDto requestDto = SignUpRequestDto.builder()
                    .email("andres@example,com")
                    .password("A12hello")
                    .build();

            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(requestDto))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);

            List<ErrorMessage> errors = objectMapper.convertValue(validatableResponse.extract().jsonPath().get("error"), new TypeReference<>() {
            });
            AssertionsForInterfaceTypes.assertThat(errors)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(error -> {
                        assertThat(error.getTimestamp()).isInstanceOf(LocalDateTime.class);
                        assertThat(error.getCode()).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY);
                        assertThat(error.getDetail()).isEqualTo("Invalid email format");
                    });
        }

        @Test
        @DisplayName("Should return an error if password is invalid")
        void error_passwordValidation() throws JsonProcessingException {
            SignUpRequestDto requestDto = SignUpRequestDto.builder()
                    .email("andres@example.com")
                    .password("A12helloA")
                    .build();

            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(requestDto))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);

            List<ErrorMessage> errors = objectMapper.convertValue(validatableResponse.extract().jsonPath().get("error"), new TypeReference<>() {
            });
            AssertionsForInterfaceTypes.assertThat(errors)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(error -> {
                        assertThat(error.getTimestamp()).isInstanceOf(LocalDateTime.class);
                        assertThat(error.getCode()).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY);
                        assertThat(error.getDetail()).isEqualTo("Password must have 1 uppercase letter, 2 digits, and be 8-12 characters long");
                    });
        }

        @Test
        @DisplayName("Should return an error if an user already exists")
        void error_duplicateUser() throws JsonProcessingException {
            SignUpRequestDto requestDto = SignUpRequestDto.builder()
                    .email("andres@example.com")
                    .password("A12hello")
                    .build();

            given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(requestDto))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(HttpStatus.SC_CREATED);

            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(requestDto))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(HttpStatus.SC_CONFLICT);

            List<ErrorMessage> errors = objectMapper.convertValue(validatableResponse.extract().jsonPath().get("error"), new TypeReference<>() {
            });
            AssertionsForInterfaceTypes.assertThat(errors)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(error -> {
                        assertThat(error.getTimestamp()).isInstanceOf(LocalDateTime.class);
                        assertThat(error.getCode()).isEqualTo(HttpStatus.SC_CONFLICT);
                        assertThat(error.getDetail()).isEqualTo("User already exists");
                    });
        }
    }


    @Nested
    @DisplayName("Login")
    class LoginTest {
        @Test
        @DisplayName("Should login an user and return user information")
        void success() throws JsonProcessingException {
            SignUpRequestDto signUpRequest = SignUpRequestDto.builder()
                    .email("andres@example.com")
                    .password("A12hello")
                    .name("Andres")
                    .phones(List.of(PhoneDto.builder()
                            .countryCode("51")
                            .cityCode(1)
                            .number(12345678L)
                            .build()))
                    .build();

            ValidatableResponse signUpResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(signUpRequest))
                    .when()
                    .post("/sign-up")
                    .then()
                    .statusCode(HttpStatus.SC_CREATED);

            String newUserToken = objectMapper.convertValue(signUpResponse.extract().jsonPath().get("token"), String.class);

            LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                    .token(newUserToken)
                    .build();

            ValidatableResponse loginResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(loginRequestDto))
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.SC_OK);

            assertThat(objectMapper.convertValue(loginResponse.extract().jsonPath().get(""), LoginResponseDto.class))
                    .satisfies(actual -> {
                        assertThat(actual.getId()).isInstanceOf(UUID.class);
                        assertThat(actual.getCreated()).isInstanceOf(LocalDateTime.class);
                        assertThat(actual.getLastLogin()).isInstanceOf(LocalDateTime.class);
                        assertThat(actual.getToken()).isNotBlank();
                        assertThat(actual.getIsActive()).isTrue();
                        assertThat(actual.getName()).isEqualTo(signUpRequest.getName());
                        assertThat(actual.getEmail()).isEqualTo(signUpRequest.getEmail());
                        assertThat(actual.getPassword()).isNotBlank().isNotEqualTo(signUpRequest.getPassword());
                        AssertionsForInterfaceTypes.assertThat(actual.getPhones())
                                .isNotEmpty()
                                .hasSize(1)
                                .containsAll(signUpRequest.getPhones());
                    });
        }

        @Test
        @DisplayName("Should return error when using an invalid token")
        void error_invalidToken() throws JsonProcessingException {
            LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                    .token("invalid-token")
                    .build();

            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(loginRequestDto))
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED);

            List<ErrorMessage> errors = objectMapper.convertValue(validatableResponse.extract().jsonPath().get("error"), new TypeReference<>() {
            });
            AssertionsForInterfaceTypes.assertThat(errors)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(error -> {
                        assertThat(error.getTimestamp()).isInstanceOf(LocalDateTime.class);
                        assertThat(error.getCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
                        assertThat(error.getDetail()).isEqualTo("Invalid or expired token");
                    });
        }

        @Test
        @DisplayName("Should return error if user doesn't exist")
        void error_userNotFound() throws JsonProcessingException {
            LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                    .token(jwtUtil.generateToken("random-user"))
                    .build();

            ValidatableResponse validatableResponse = given()
                    .contentType(ContentType.JSON)
                    .body(objectMapper.writeValueAsString(loginRequestDto))
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);

            List<ErrorMessage> errors = objectMapper.convertValue(validatableResponse.extract().jsonPath().get("error"), new TypeReference<>() {
            });
            AssertionsForInterfaceTypes.assertThat(errors)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(error -> {
                        assertThat(error.getTimestamp()).isInstanceOf(LocalDateTime.class);
                        assertThat(error.getCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
                        assertThat(error.getDetail()).isEqualTo("User not found");
                    });
        }
    }
}