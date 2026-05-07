package com.onlinecourses.billing.infrastructure.repository;

import com.onlinecourses.billing.application.port.PaymentOrderRepository;
import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("memory")
public class InMemoryPaymentOrderRepository implements PaymentOrderRepository {

    private final Map<UUID, PaymentOrder> paymentOrdersById = new HashMap<>();
    private final Map<UUID, PaymentOrder> pendingPaymentOrdersByEnrollmentId = new HashMap<>();

    @Override
    public boolean existsPendingByEnrollmentId(UUID enrollmentId) {
        return pendingPaymentOrdersByEnrollmentId.containsKey(enrollmentId);
    }

    @Override
    public Optional<PaymentOrder> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public PaymentOrder save(PaymentOrder paymentOrder) {
        paymentOrdersById.put(paymentOrder.getId(), paymentOrder);

        System.out.println(paymentOrder.getStatus());

        if (paymentOrder.getStatus() == PaymentOrderStatus.PENDING) {
            pendingPaymentOrdersByEnrollmentId.put(
                    paymentOrder.getEnrollmentId(),
                    paymentOrder
            );
        }

        return paymentOrder;
    }
}