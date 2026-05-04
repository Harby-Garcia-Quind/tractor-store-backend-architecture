package com.onlinecourses.identity.application.response;

import com.onlinecourses.identity.domain.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        String role,
        String status,
        LocalDateTime createdAt
) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail().value(),
                user.getRole().name(),
                user.getStatus().name(),
                user.getCreatedAt()
        );
    }

}
