package com.onlinecourses.enrollment.infrastructure.persistence.mapper;

import com.onlinecourses.enrollment.domain.model.Enrollment;
import com.onlinecourses.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;

public class EnrollmentJpaMapper {
    private EnrollmentJpaMapper() {}

    public static EnrollmentJpaEntity toEntity(Enrollment enrollment) {
        return new EnrollmentJpaEntity(
                enrollment.getId(),
                enrollment.getUserId(),
                enrollment.getCourseId(),
                enrollment.getStatus(),
                enrollment.getCreatedAt()
        );
    }

    public static Enrollment toDomain(EnrollmentJpaEntity entity) {
        return Enrollment.rehydrate(
                entity.getId(),
                entity.getUserId(),
                entity.getCourseId(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
