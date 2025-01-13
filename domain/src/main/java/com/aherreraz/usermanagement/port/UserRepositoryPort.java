package com.aherreraz.usermanagement.port;

import com.aherreraz.usermanagement.model.User;

public interface UserRepositoryPort {
    User create(User user);
    User findByCredentials(String email, String password);
}
