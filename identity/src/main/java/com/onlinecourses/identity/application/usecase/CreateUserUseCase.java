package com.onlinecourses.identity.application.usecase;

import com.onlinecourses.identity.application.command.CreateUserCommand;
import com.onlinecourses.identity.application.port.UserRepository;
import com.onlinecourses.identity.application.response.UserResponse;
import com.onlinecourses.identity.domain.exception.UserAlreadyExistsException;
import com.onlinecourses.identity.domain.model.User;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse execute(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException(command.email());
        }

        User user = User.create(
                command.fullName(),
                command.email(),
                command.role()
        );

        User savedUser = userRepository.save(user);

        return UserResponse.fromDomain(savedUser);
    }


}
