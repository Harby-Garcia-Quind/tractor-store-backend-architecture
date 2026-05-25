package com.onlinecourses.billing.application.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecourses.billing.application.command.CreatePaymentOrderCommand;
import com.onlinecourses.billing.application.usecase.CreatePaymentOrderUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentCreateListener {

    private final CreatePaymentOrderUseCase createPaymentOrderUseCase;
    private final ObjectMapper objectMapper;

    public EnrollmentCreateListener(CreatePaymentOrderUseCase createPaymentOrderUseCase, ObjectMapper objectMapper) {
        this.createPaymentOrderUseCase = createPaymentOrderUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "enrollment-created")
    public void onEnrollmentCreate(CreatePaymentOrderCommand command) {
        try {
            // Spring ya convirtió el JSON a el objeto 'command' automáticamente
            createPaymentOrderUseCase.execute(command);
        } catch (Exception e) {
            System.err.println("Error procesando evento: " + e.getMessage());
        }
    }

}
