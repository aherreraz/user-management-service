# User Authentication API

Basic user authentication service. Supports signing up new users and retrieving registered user information when providing a token.

## Features

- **Hexagonal Architecture**
  - Separation of concerns between core business logic and external concerns
  - Ensures core domain logic is not dependent on specific external implementations
- **Jwt Token Handling**
  - Secure token generation and validation
- **Validation**
  - Bean validation using Java Validation API


## Modules

- `domain`:
    - Contains core business logic and domain entities
    - Has no dependencies on external libraries
    - Defines ports (interfaces) for communication with external systems
- `application`:
    - Handles orchestration of use cases and business processes
    - Maps DTOs to domain objects and vice versa
- `infrastructure`:
    - Implements `domain` ports
    - Manages persistence
    - Exposes REST endpoints for `sign-up` and `login`

## Technologies

- Java 11
- Spring Boot 2.5.14
- Gradle 7.4
- MapStruct (for object mapping)
- Jackson (for json parsing)
- Junit 5
- AssertJ
- Mockito (for testing)
- RestAssured (for integration testing)

## Getting Started

### Prerequisites

- Java 11+

### Build

```shell
./gradlew build
```

### Run

```shell
./gradlew bootRun
```

### Execute tests

```shell
./gradlew test
```

## API Endpoints

### Sign Up

- **URL**: `/sign-up`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string",
    "phones": [
      {
        "number": long,
        "cityCode": integer,
        "countryCode": "string"
      }
    ]
  }
  ```

- **Responses**:

  | Code | Description                 |
  |------|-----------------------------|
  | 201  | Successfully signed up user |
  | 409  | User already exists         |
  | 422  | Validation error            |

- **Successful response body**

  ```json
  {
    "id": "uuid",
    "created": "datetime",
    "lastLogin": "datetime",
    "token": "string",
    "isActive": boolean
  }
  ```
  
- **Error response body**

  ```json
  {
    "error": [
      {
        "timestamp": "datetime",
        "code": integer,
        "detail": "string"
      }
    ]
  }
  ```
  
### Login

- **URL**: `/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "token": "string"
  }
  ```

- **Responses**:

  | Code | Description              |
  |------|--------------------------|
  | 200  | User logged in           |
  | 401  | Invalid or expired token |
  | 404  | User not found           |

- **Successful response body**

  ```json
  {
    "id": "uuid",
    "created": "datetime",
    "lastLogin": "datetime",
    "token": "string",
    "isActive": boolean,
    "name": "string",
    "email": "string",
    "password": "string",
    "phones": [
      {
        "number": long,
        "cityCode": integer,
        "countryCode": "string"
      }
    ]
  }
  ```

- **Error response body**

  ```json
  {
    "error": [
      {
        "timestamp": "datetime",
        "code": integer,
        "detail": "string"
      }
    ]
  }
  ```