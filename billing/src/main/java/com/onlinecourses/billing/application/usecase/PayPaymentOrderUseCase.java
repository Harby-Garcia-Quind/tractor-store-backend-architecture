package com.onlinecourses.billing.application.usecase;

import com.onlinecourses.billing.application.port.PaymentOrderRepository;
import com.onlinecourses.billing.application.response.PaymentOrderResponse;
import com.onlinecourses.billing.domain.exception.PaymentOrderNotFoundException;
import com.onlinecourses.billing.domain.model.PaymentOrder;

import java.util.UUID;

public class PayPaymentOrderUseCase {

    private final PaymentOrderRepository paymentOrderRepository;

    public PayPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
    }

    public PaymentOrderResponse execute (UUID paymentOrderId) {
        PaymentOrder paymentOrder = paymentOrderRepository
                .findById(paymentOrderId)
                .orElseThrow(() -> new PaymentOrderNotFoundException(paymentOrderId));

        paymentOrder.pay();

        PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);

        return PaymentOrderResponse.fromDomain(savedPaymentOrder);

    }

}
