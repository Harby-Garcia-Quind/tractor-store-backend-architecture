package com.onlinecourses.identity.application.command;

import com.onlinecourses.identity.domain.model.enums.UserRole;

public record CreateUserCommand(
        String fullName,
        String email,
        UserRole role
) {
}