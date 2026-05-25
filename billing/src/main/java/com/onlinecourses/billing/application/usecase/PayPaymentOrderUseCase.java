package com.onlinecourses.billing.application.usecase;

import com.onlinecourses.billing.application.port.PaymentOrderRepository;
import com.onlinecourses.billing.application.response.PaymentOrderResponse;
import com.onlinecourses.billing.domain.exception.PaymentOrderNotFoundException;
import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.enrollment.application.api.EnrollmentModuleApi;
import jakarta.transaction.Transactional;

import java.util.UUID;

public class PayPaymentOrderUseCase {

    private final PaymentOrderRepository paymentOrderRepository;
    private final EnrollmentModuleApi enrollmentModuleApi;

    public PayPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository, EnrollmentModuleApi enrollmentModuleApi) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.enrollmentModuleApi = enrollmentModuleApi;
    }

    @Transactional
    public PaymentOrderResponse execute (UUID paymentOrderId) {
        PaymentOrder paymentOrder = paymentOrderRepository
                .findById(paymentOrderId)
                .orElseThrow(() -> new PaymentOrderNotFoundException(paymentOrderId));

        paymentOrder.pay();

        PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);

        enrollmentModuleApi.activateEnrollment(paymentOrder.getEnrollmentId());

        return PaymentOrderResponse.fromDomain(savedPaymentOrder);

    }

}
