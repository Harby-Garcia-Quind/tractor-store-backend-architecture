package com.onlinecourses.billing.application.usecase;

import com.onlinecourses.billing.application.command.CreatePaymentOrderCommand;
import com.onlinecourses.billing.application.port.PaymentOrderRepository;
import com.onlinecourses.billing.application.response.PaymentOrderResponse;
import com.onlinecourses.billing.domain.exception.EnrollmentNotAvailableForPaymentOrderException;
import com.onlinecourses.billing.domain.exception.PaymentOrderAlreadyExistsException;
import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.enrollment.application.api.EnrollmentModuleApi;

public class CreatePaymentOrderUseCase {

    private final PaymentOrderRepository paymentOrderRepository;
    private final EnrollmentModuleApi enrollmentModuleApi;

    public CreatePaymentOrderUseCase(EnrollmentModuleApi enrollmentModuleApi, PaymentOrderRepository paymentOrderRepository) {
        this.enrollmentModuleApi = enrollmentModuleApi;
        this.paymentOrderRepository = paymentOrderRepository;
    }

    public PaymentOrderResponse execute(CreatePaymentOrderCommand command) {
        validate(command);

        PaymentOrder paymentOrder = PaymentOrder.create(
                command.enrollmentId(),
                command.amount()
        );

        PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);

        return PaymentOrderResponse.fromDomain(savedPaymentOrder);

    }

    private void validate(CreatePaymentOrderCommand command) {
        if (!enrollmentModuleApi.existsPendingEnrollment(command.enrollmentId())) {
            throw new EnrollmentNotAvailableForPaymentOrderException(command.enrollmentId());
        }

        if (paymentOrderRepository.existsPendingByEnrollmentId(command.enrollmentId())) {
            throw new PaymentOrderAlreadyExistsException(command.enrollmentId());
        }

    }

}
