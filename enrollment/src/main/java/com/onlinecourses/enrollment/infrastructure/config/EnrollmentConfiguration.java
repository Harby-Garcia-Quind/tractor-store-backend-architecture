package com.onlinecourses.enrollment.infrastructure.config;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
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
            CatalogModuleApi catalogModuleApi
    ) {
        return new CreateEnrollmentUseCase(
                enrollmentRepository,
                identityModuleApi,
                catalogModuleApi
        );
    }


}
