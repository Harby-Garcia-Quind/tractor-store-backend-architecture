package com.onlinecourses.enrollment.application.port;

import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {

    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);

    boolean existsByIdAndStatus(UUID enrollmentId, EnrollmentStatus status);

    Optional<Enrollment> findById(UUID enrollmentId);

    Enrollment save(Enrollment enrollment);
}
