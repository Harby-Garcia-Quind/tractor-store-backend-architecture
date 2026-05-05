package com.onlinecourses.enrollment.domain.exception;

import java.util.UUID;

public class UserNotAvailableForEnrollmentException extends RuntimeException {
    public UserNotAvailableForEnrollmentException(UUID userId) {
        super("El usuario no existe o no está activo para inscripción: " + userId);
    }
}
