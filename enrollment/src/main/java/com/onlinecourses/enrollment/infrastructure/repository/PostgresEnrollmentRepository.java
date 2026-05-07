package com.onlinecourses.enrollment.infrastructure.repository;

import com.onlinecourses.enrollment.application.port.EnrollmentPublisher;
import com.onlinecourses.enrollment.application.port.EnrollmentRepository;
import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;
import com.onlinecourses.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import com.onlinecourses.enrollment.infrastructure.persistence.mapper.EnrollmentJpaMapper;
import com.onlinecourses.enrollment.infrastructure.persistence.repository.EnrollmentJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Profile("postgres")
public class PostgresEnrollmentRepository implements EnrollmentRepository {

    private final EnrollmentJpaRepository repository;

    public PostgresEnrollmentRepository(EnrollmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByUserIdAndCourseId(UUID userId, UUID courseId) {
        return repository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public boolean existsByIdAndStatus(UUID enrollmentId, EnrollmentStatus status) {
        return repository.existsByIdAndStatus(enrollmentId,status);
    }

    @Override
    public void activateEnrollment(UUID enrollmentId) {

    }

    @Override
    public Enrollment save(Enrollment enrollment) {

        EnrollmentJpaEntity entity = EnrollmentJpaMapper.toEntity(enrollment);
        EnrollmentJpaEntity savedEntity = repository.save(entity);

        return EnrollmentJpaMapper.toDomain(savedEntity);
    }
}
