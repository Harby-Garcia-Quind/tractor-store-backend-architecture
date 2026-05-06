package com.onlinecourses.billing.application.port;

import com.onlinecourses.billing.domain.model.PaymentOrder;

import java.util.Optional;
import java.util.UUID;

public interface PaymentOrderRepository {

    boolean existsPendingByEnrollmentId(UUID enrollmentId);

    Optional<PaymentOrder> findById(UUID id);

    PaymentOrder save(PaymentOrder paymentOrder);


}
