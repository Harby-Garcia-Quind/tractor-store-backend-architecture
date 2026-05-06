package com.onlinecourses.billing.application.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentOrderCommand(
        UUID enrollmentId,
        BigDecimal amount
) {
}