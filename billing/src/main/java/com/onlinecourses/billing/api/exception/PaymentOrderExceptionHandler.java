package com.onlinecourses.billing.api.exception;

import com.onlinecourses.billing.api.response.ApiResponse;
import com.onlinecourses.billing.domain.exception.EnrollmentNotAvailableForPaymentOrderException;
import com.onlinecourses.billing.domain.exception.InvalidPaymentOrderException;
import com.onlinecourses.billing.domain.exception.PaymentOrderAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.onlinecourses.billing")
public class PaymentOrderExceptionHandler {

    @ExceptionHandler(EnrollmentNotAvailableForPaymentOrderException.class)
    public ResponseEntity<ApiResponse<Object>> handleEnrollmentNotAvailable(EnrollmentNotAvailableForPaymentOrderException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(PaymentOrderAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handlePaymentOrderAlreadyExists(
            PaymentOrderAlreadyExistsException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()
                ));
    }


    @ExceptionHandler(InvalidPaymentOrderException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidPayment(
            InvalidPaymentOrderException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<com.onlinecourses.enrollment.api.response.ApiResponse<Object>> handleInvalidJson(
            HttpMessageNotReadableException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(com.onlinecourses.enrollment.api.response.ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "El cuerpo de la petición es inválido o contiene valores no permitidos."
                ));
    }


}
