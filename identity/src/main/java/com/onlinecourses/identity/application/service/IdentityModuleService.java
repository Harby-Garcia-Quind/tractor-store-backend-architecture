package com.onlinecourses.identity.application.service;

import com.onlinecourses.identity.application.api.IdentityModuleApi;
import com.onlinecourses.identity.application.port.UserRepository;

import java.util.UUID;

public class IdentityModuleService implements IdentityModuleApi {

    private final UserRepository userRepository;

    public IdentityModuleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsActiveUser(UUID userId) {
        return userRepository.existsActiveUserById(userId);
    }
}
