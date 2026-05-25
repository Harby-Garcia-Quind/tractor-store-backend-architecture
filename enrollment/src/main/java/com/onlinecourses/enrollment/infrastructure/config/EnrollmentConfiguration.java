package com.onlinecourses.enrollment.infrastructure.config;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.enrollment.application.api.EnrollmentModuleApi;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.service.EnrollmentModuleService;
import com.onlinecourses.enrollment.application.usecase.CreateEnrollmentUseCase;
import com.onlinecourses.enrollment.application.usecase.GetActiveEnrollmentsByUserUseCase;
import com.onlinecourses.enrollment.application.usecase.UpdateEnrollmentToCancelledUseCase;
import com.onlinecourses.identity.application.api.IdentityModuleApi;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class EnrollmentConfiguration {

    @Bean
    public CreateEnrollmentUseCase createEnrollmentUseCase(
            EnrollmentRepository enrollmentRepository,
            IdentityModuleApi identityModuleApi,
            CatalogModuleApi catalogModuleApi,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        return new CreateEnrollmentUseCase(
                enrollmentRepository,
                identityModuleApi,
                catalogModuleApi,
                kafkaTemplate
        );
    }

    @Bean
    public GetActiveEnrollmentsByUserUseCase getActiveEnrollmentsByUserUseCase(EnrollmentRepository enrollmentRepository) {
        return new GetActiveEnrollmentsByUserUseCase(enrollmentRepository);
    }

    @Bean
    public EnrollmentModuleApi enrollmentModuleApi(EnrollmentRepository enrollmentRepository) {
        return new EnrollmentModuleService(enrollmentRepository);
    }

    @Bean
    public UpdateEnrollmentToCancelledUseCase updateEnrollmentToCancelledUseCase(EnrollmentRepository enrollmentRepository) {
        return new UpdateEnrollmentToCancelledUseCase(enrollmentRepository);
    }


}
