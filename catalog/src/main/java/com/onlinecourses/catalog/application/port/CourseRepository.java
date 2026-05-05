package com.onlinecourses.catalog.application.port;

import com.onlinecourses.catalog.domain.model.Course;

import java.util.UUID;

public interface CourseRepository {

    boolean existsByTitle(String title);

    boolean existsById(UUID courseId);

    boolean existsActiveCourseById(UUID courseId);

    Course save(Course course);
}