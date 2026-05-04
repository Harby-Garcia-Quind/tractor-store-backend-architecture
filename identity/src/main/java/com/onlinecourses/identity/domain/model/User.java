package com.onlinecourses.identity.domain.model;

import com.onlinecourses.identity.domain.model.enums.UserRole;
import com.onlinecourses.identity.domain.model.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID id;
    private String fullName;
    private Email email;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;


    private User(UUID id, String fullName, Email email, UserRole role, UserStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static User create(String fullName, String email, UserRole role) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }

        if (role == null) {
            throw new IllegalArgumentException("El rol del usuario no puede ser null");
        }

        return new User(
                UUID.randomUUID(),
                fullName,
                new Email(email),
                role,
                UserStatus.ACTIVE,
                LocalDateTime.now()
        );
    }


    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Email getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
