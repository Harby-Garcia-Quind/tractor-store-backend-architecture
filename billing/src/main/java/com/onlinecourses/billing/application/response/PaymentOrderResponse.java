package com.onlinecourses.billing.application.response;

import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentOrderResponse(
        UUID id,
        UUID enrollmentId,
        BigDecimal amount,
        PaymentOrderStatus status,
        LocalDateTime createdAt
) {

    public static PaymentOrderResponse fromDomain(PaymentOrder paymentOrder) {
        return new PaymentOrderResponse(
                paymentOrder.getId(),
                paymentOrder.getEnrollmentId(),
                paymentOrder.getAmount(),
                paymentOrder.getStatus(),
                paymentOrder.getCreatedAt()
        );
    }


}
