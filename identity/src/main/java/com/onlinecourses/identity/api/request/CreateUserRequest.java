package com.onlinecourses.identity.api.request;

import com.onlinecourses.identity.domain.model.enums.UserRole;

public record CreateUserRequest(
        String fullName,
        String email,
        UserRole role
) {
}
