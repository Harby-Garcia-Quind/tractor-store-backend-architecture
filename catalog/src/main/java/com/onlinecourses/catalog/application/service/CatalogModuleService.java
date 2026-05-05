package com.onlinecourses.catalog.application.service;

import com.onlinecourses.catalog.application.api.CatalogModuleApi;
import com.onlinecourses.catalog.application.port.CourseRepository;

import java.util.UUID;

public class CatalogModuleService implements CatalogModuleApi {

    private final CourseRepository courseRepository;

    public CatalogModuleService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public boolean existsActiveCourse(UUID courseId) {
        return courseRepository.existsActiveCourseById(courseId);
    }
}
