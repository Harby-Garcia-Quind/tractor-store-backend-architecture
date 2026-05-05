package com.onlinecourses.enrollment.infrastructure.repository;

import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.domain.model.Enrollment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("memory")
public class InMemoryEnrollmentRepository implements EnrollmentRepository {

    private final Map<UUID, Enrollment> enrollmentsById = new HashMap<>();
    private final Map<String, Enrollment> enrollmentsByUserAndCourse = new HashMap<>();

    @Override
    public boolean existsByUserIdAndCourseId(UUID userId, UUID courseId) {
        return enrollmentsByUserAndCourse.containsKey(buildKey(userId, courseId));
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        enrollmentsById.put(enrollment.getId(), enrollment);
        enrollmentsByUserAndCourse.put(
                buildKey(enrollment.getUserId(), enrollment.getCourseId()),
                enrollment
        );

        return enrollment;
    }

    private String buildKey(UUID userId, UUID courseId) {
        return userId + ":" + courseId;
    }
}
