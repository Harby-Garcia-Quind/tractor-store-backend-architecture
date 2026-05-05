package com.onlinecourses.enrollment.domain.exception;

import java.util.UUID;

public class CourseNotAvailableForEnrollmentException extends RuntimeException {
    public CourseNotAvailableForEnrollmentException(UUID courseId) {
        super("El curso no existe o no está activo para inscripción: " + courseId);
    }
}
