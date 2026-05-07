package com.onlinecourses.enrollment.domain.exception;

import java.util.UUID;

public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(UUID enrollmentId) {
        super("No existe una inscripción con id: " + enrollmentId);
    }
}