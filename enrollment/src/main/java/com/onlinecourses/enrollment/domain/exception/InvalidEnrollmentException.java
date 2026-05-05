package com.onlinecourses.enrollment.domain.exception;

import java.util.UUID;

public class InvalidEnrollmentException extends RuntimeException {
    public InvalidEnrollmentException(String message) {
        super(message);
    }
}
