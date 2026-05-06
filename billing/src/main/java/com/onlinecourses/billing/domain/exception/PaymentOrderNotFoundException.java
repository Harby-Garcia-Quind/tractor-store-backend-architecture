package com.onlinecourses.billing.domain.exception;

import java.util.UUID;

public class PaymentOrderNotFoundException extends RuntimeException {

    public PaymentOrderNotFoundException(UUID paymentOrderId) {
        super("No existe una orden de pago con id: " + paymentOrderId);
    }
}