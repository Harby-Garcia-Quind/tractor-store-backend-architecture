package com.onlinecourses.enrollment.application.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateEnrollmentKafkaEvent(
        UUID enrollmentId,
        BigDecimal amount
) {
    @Override
    public String toString() {
        return """
                {
                    "enrollmentId": "%s",
                    "amount": %d
                }
                """
                .formatted(enrollmentId, amount.longValueExact());
    }
}
