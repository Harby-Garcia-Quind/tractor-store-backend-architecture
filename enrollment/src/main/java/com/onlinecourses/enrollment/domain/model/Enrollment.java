package com.onlinecourses.enrollment.domain.model;

import com.onlinecourses.enrollment.domain.exception.InvalidEnrollmentException;
import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Enrollment {

    private UUID id;
    private UUID userId;
    private UUID courseId;
    private EnrollmentStatus status;
    LocalDateTime createdAt;

    private Enrollment(UUID id, UUID userId, UUID courseId, EnrollmentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Enrollment create(UUID userId, UUID courseId) {
        validate(userId,courseId);

        return new Enrollment(
                UUID.randomUUID(),
                userId,
                courseId,
                EnrollmentStatus.PENDING_PAYMENT,
                LocalDateTime.now()
        );

    }

    public static Enrollment rehydrate(
            UUID id,
            UUID userId,
            UUID courseId,
            EnrollmentStatus status,
            LocalDateTime createdAt
    ) {
        validate(userId,courseId);

        if(id == null) {
            throw new InvalidEnrollmentException("El ID no puede ser nulo");
        }

        if(status == null) {
            throw new InvalidEnrollmentException("El estado no puede ser nulo");
        }
        if(createdAt == null) {
            throw new InvalidEnrollmentException("La fecha de creación no puede ser nula");
        }

        return new Enrollment(
                id,
                userId,
                courseId,
                status,
                createdAt
        );
    }

    private static void validate(UUID userId, UUID courseId) {
        if (userId == null) {
            throw new InvalidEnrollmentException("El userId no puede ser null");
        }

        if (courseId == null) {
            throw new InvalidEnrollmentException("El courseId no puede ser null");
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
