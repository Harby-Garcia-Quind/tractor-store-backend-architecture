package com.onlinecourses.enrollment.application.port;

import com.onlinecourses.enrollment.domain.model.Enrollment;

import java.util.UUID;

public interface EnrollmentRepository {
    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
    Enrollment save(Enrollment enrollment);
}
