package com.onlinecourses.enrollment.infrastructure.persistence.repository;

import com.onlinecourses.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {

    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);

    boolean existsByIdAndStatus(UUID enrollmentId, String status);
}
