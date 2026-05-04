package com.onlinecourses.identity.infrastructure.config;


import com.onlinecourses.identity.application.api.IdentityModuleApi;
import com.onlinecourses.identity.application.port.UserRepository;
import com.onlinecourses.identity.application.service.IdentityModuleService;
import com.onlinecourses.identity.application.usecase.CreateUserUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentityConfiguration {

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    @Bean
    public IdentityModuleApi identityModuleApi(UserRepository userRepository) {
        return new IdentityModuleService(userRepository);
    }
}