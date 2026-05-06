package com.onlinecourses.billing.domain.exception;

import java.util.UUID;

public class EnrollmentNotAvailableForPaymentOrderException extends RuntimeException {
    public EnrollmentNotAvailableForPaymentOrderException(UUID enrollmentId) {
        super("La inscripción no existe o no está pendiente de pago: " + enrollmentId);
    }
}
