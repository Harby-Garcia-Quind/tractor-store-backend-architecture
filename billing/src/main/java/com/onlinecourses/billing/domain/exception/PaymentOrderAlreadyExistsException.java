package com.onlinecourses.billing.domain.exception;

import java.util.UUID;

public class PaymentOrderAlreadyExistsException extends RuntimeException {
    public PaymentOrderAlreadyExistsException(UUID enrollmentId) {
        super("Ya existe una orden de pago pendiente para la inscripción: " + enrollmentId);
    }
}
