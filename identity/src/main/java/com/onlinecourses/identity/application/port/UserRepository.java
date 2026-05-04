package com.onlinecourses.identity.application.port;

import com.onlinecourses.identity.domain.model.User;

import java.util.UUID;

public interface UserRepository {

    boolean existsByEmail(String email);
    boolean existsActiveUserById(UUID userId);
    boolean existsById(UUID userId);
    User save(User user);
}
