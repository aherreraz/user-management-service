package com.aherreraz.usermanagement.port;

import com.aherreraz.usermanagement.model.User;

public interface UserRepositoryPort {
    User save(User user);
    Boolean exists(String email);
    User findByCredentials(String email, String password);
}
