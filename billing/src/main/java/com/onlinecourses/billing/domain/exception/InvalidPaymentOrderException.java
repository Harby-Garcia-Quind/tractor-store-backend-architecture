package com.onlinecourses.billing.domain.exception;

public class InvalidPaymentOrderException extends RuntimeException {
    public InvalidPaymentOrderException(String message) {
        super(message);
    }
}
