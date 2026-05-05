package com.onlinecourses.catalog.domain.model;

import com.onlinecourses.catalog.domain.exception.InvalidCourseException;
import com.onlinecourses.catalog.domain.model.enums.CourseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Course {

    private final UUID id;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final CourseStatus status;
    private final LocalDateTime createdAt;

    private Course(UUID id, String title, String description, BigDecimal price, CourseStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Course create(String title, String description, BigDecimal price) {
        validate(title, description, price);
        return new Course(
                UUID.randomUUID(),
                title,
                description,
                price,
                CourseStatus.ACTIVE,
                LocalDateTime.now()
        );
    }

    public static Course rehydrate(UUID id, String title, String description, BigDecimal price, CourseStatus status, LocalDateTime createdAt) {
        validate(title, description, price);

        if (id == null) {
            throw new InvalidCourseException("El ID del curso no puede ser nulo");
        }
        if (status == null) {
            throw new InvalidCourseException("El estado del curso no puede ser nulo");
        }
        if (createdAt == null) {
            throw new InvalidCourseException("La fecha de creacion del curso no puede ser nula");
        }

        return new Course(
                id,
                title.trim(),
                description,
                price,
                status,
                createdAt
        );
    }

    private static void validate(String title, String description, BigDecimal price) {
        if (title == null || title.isBlank()) {
            throw new InvalidCourseException("El título no puede estar vacío");
        }

        if (description == null || description.isBlank()) {
            throw new InvalidCourseException("La descripción no puede estar vacía");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidCourseException("El precio debe ser mayor a cero");
        }
    }

    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public CourseStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
