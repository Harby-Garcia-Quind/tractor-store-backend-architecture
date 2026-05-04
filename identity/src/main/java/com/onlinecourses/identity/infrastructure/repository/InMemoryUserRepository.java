package com.onlinecourses.identity.infrastructure.repository;

import com.onlinecourses.identity.application.port.UserRepository;
import com.onlinecourses.identity.domain.model.User;
import com.onlinecourses.identity.domain.model.enums.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> usersByEmail = new HashMap<>();
    private final Map<UUID, User> usersById = new HashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(email);
    }

    @Override
    public boolean existsActiveUserById(UUID userId) {
        return usersById.containsKey(userId);
    }

    @Override
    public boolean existsById(UUID userId) {
        User user = usersById.get(userId);

        return user != null && user.getStatus() == UserStatus.ACTIVE;
    }

    @Override
    public User save(User user) {
        String email = user.getEmail().value();
        usersByEmail.put(email, user);
        return user;
    }
}
