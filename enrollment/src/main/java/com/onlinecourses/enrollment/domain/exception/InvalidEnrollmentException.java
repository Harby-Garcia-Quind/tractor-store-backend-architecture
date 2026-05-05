package com.onlinecourses.enrollment.domain.exception;

public class InvalidEnrollmentException extends RuntimeException {
    public InvalidEnrollmentException(String message) {
        super(message);
    }
}
