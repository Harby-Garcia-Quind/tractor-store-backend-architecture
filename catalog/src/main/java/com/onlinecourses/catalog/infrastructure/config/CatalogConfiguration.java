package com.onlinecourses.catalog.infrastructure.config;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.catalog.application.port.CourseRepository;
import com.onlinecourses.catalog.application.service.CatalogModuleService;
import com.onlinecourses.catalog.application.usecase.CreateCourseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfiguration {

    @Bean
    public CreateCourseUseCase createCourseUseCase(CourseRepository courseRepository) {
        return new CreateCourseUseCase(courseRepository);
    }

    @Bean
    public CatalogModuleApi catalogModuleApi(CourseRepository courseRepository) {
        return new CatalogModuleService(courseRepository);
    }

}
