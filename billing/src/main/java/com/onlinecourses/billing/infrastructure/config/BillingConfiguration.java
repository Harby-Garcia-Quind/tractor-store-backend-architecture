package com.onlinecourses.billing.infrastructure.config;

import com.onlinecourses.billing.application.port.PaymentOrderRepository;
import com.onlinecourses.billing.application.usecase.CreatePaymentOrderUseCase;
import com.onlinecourses.billing.application.usecase.PayPaymentOrderUseCase;
import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;
import com.onlinecourses.enrollment.application.api.EnrollmentModuleApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillingConfiguration {

    @Bean
    public CreatePaymentOrderUseCase createPaymentOrderUseCase(
            PaymentOrderRepository paymentOrderRepository,
            EnrollmentModuleApi enrollmentModuleApi
    ) {
        return new CreatePaymentOrderUseCase(enrollmentModuleApi, paymentOrderRepository);
    }

    @Bean
    public PayPaymentOrderUseCase payPaymentOrderUseCase(PaymentOrderRepository paymentOrderRepository, EnrollmentModuleApi enrollmentModuleApi) {
        return new PayPaymentOrderUseCase(
                paymentOrderRepository,
                enrollmentModuleApi
        );
    }


}
