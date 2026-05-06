package com.onlinecourses.billing.api.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentOrderRequest(
        UUID enrollmentId,
        BigDecimal amount
) {
}
