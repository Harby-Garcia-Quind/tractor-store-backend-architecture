package com.onlinecourses.enrollment.api.exception;

import com.onlinecourses.enrollment.api.response.ApiResponse;
import com.onlinecourses.enrollment.domain.exception.CourseNotAvailableForEnrollmentException;
import com.onlinecourses.enrollment.domain.exception.EnrollmentAlreadyExistsException;
import com.onlinecourses.enrollment.domain.exception.InvalidEnrollmentException;
import com.onlinecourses.enrollment.domain.exception.UserNotAvailableForEnrollmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.onlinecourses.enrollment")
public class EnrollmentExceptionHandler {

    @ExceptionHandler(UserNotAvailableForEnrollmentException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotAvailable(
            UserNotAvailableForEnrollmentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(CourseNotAvailableForEnrollmentException.class)
    public ResponseEntity<ApiResponse<Object>> handleCourseNotAvailable(
            CourseNotAvailableForEnrollmentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(EnrollmentAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleEnrollmentAlreadyExists(
            EnrollmentAlreadyExistsException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(InvalidEnrollmentException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidEnrollment(
            InvalidEnrollmentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidJson(
            HttpMessageNotReadableException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "El cuerpo de la petición es inválido o contiene valores no permitidos."
                ));
    }
}