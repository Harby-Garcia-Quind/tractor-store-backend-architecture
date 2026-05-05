package com.onlinecourses.enrollment.infrastructure.persistence.entity;

import com.onlinecourses.enrollment.domain.model.enums.EnrollmentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments", schema = "enrollment")
public class EnrollmentJpaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "course_id", nullable = false)
    private UUID courseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EnrollmentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected EnrollmentJpaEntity() {
    }

    public EnrollmentJpaEntity(UUID id, UUID userId, UUID courseId, EnrollmentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.status = status;
        this.createdAt = createdAt;
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
