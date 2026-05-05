package com.onlinecourses.catalog.infrastructure.repository;

import com.onlinecourses.catalog.application.port.CourseRepository;
import com.onlinecourses.catalog.domain.model.Course;
import com.onlinecourses.catalog.domain.model.enums.CourseStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("memory")
public class InMemoryCourseRepository implements CourseRepository {

    private final Map<UUID, Course> coursesById = new HashMap<>();
    private final Map<String, Course> coursesByTitle = new HashMap<>();

    @Override
    public boolean existsByTitle(String title) {
        return coursesByTitle.containsKey(normalizeTitle(title));
    }

    @Override
    public boolean existsById(UUID courseId) {
        return coursesById.containsKey(courseId);
    }

    @Override
    public boolean existsActiveCourseById(UUID courseId) {
        Course course = coursesById.get(courseId);

        return course != null && course.getStatus() == CourseStatus.ACTIVE;
    }

    @Override
    public Course save(Course course) {
        coursesById.put(course.getId(), course);
        coursesByTitle.put(normalizeTitle(course.getTitle()), course);

        return course;
    }

    private String normalizeTitle(String title) {
        return title.trim().toLowerCase();
    }
}