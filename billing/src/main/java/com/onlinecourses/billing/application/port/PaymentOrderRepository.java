package com.onlinecourses.billing.application.port;

import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOrderRepository {

    boolean existsPendingByEnrollmentId(UUID enrollmentId);

    Optional<PaymentOrder> findById(UUID id);

    PaymentOrder save(PaymentOrder paymentOrder);

    List<PaymentOrder> findByEnrollmentIdAndStatus(UUID enrollmentId, PaymentOrderStatus status);


}
