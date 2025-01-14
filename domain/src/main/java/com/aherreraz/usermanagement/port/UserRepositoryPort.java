package com.aherreraz.usermanagement.port;

import com.aherreraz.usermanagement.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Boolean exists(String email);
    Optional<User> findByEmail(String email);
}
