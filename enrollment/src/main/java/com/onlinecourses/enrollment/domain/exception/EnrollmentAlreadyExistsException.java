package com.onlinecourses.enrollment.domain.exception;

import java.util.UUID;

public class EnrollmentAlreadyExistsException extends RuntimeException {
    public EnrollmentAlreadyExistsException(UUID userId, UUID courseId) {
        super("El usuario " + userId + " ya está inscrito en el curso " + courseId);
    }
}
