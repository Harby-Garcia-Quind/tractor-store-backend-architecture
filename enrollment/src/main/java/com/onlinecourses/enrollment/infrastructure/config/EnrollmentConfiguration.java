package com.onlinecourses.enrollment.infrastructure.config;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.enrollment.application.api.EnrollmentModuleApi;
import com.onlinecourses.enrollment.application.port.EnrollmentPublisher;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.application.service.EnrollmentModuleService;
import com.onlinecourses.enrollment.application.usecase.CreateEnrollmentUseCase;
import com.onlinecourses.identity.application.api.IdentityModuleApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnrollmentConfiguration {

    @Bean
    public CreateEnrollmentUseCase createEnrollmentUseCase(
            EnrollmentRepository enrollmentRepository,
            IdentityModuleApi identityModuleApi,
            CatalogModuleApi catalogModuleApi,
            EnrollmentPublisher enrollmentPublisher
    ) {
        return new CreateEnrollmentUseCase(
                enrollmentRepository,
                identityModuleApi,
                catalogModuleApi,
                enrollmentPublisher
        );
    }

    @Bean
    public EnrollmentModuleApi enrollmentModuleApi(EnrollmentRepository enrollmentRepository) {
        return new EnrollmentModuleService(enrollmentRepository);
    }


}
