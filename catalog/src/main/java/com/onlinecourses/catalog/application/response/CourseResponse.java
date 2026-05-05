package com.onlinecourses.catalog.application.response;

import com.onlinecourses.catalog.domain.model.Course;
import com.onlinecourses.catalog.domain.model.enums.CourseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CourseResponse(
        UUID id,
        String title,
        String description,
        BigDecimal price,
        CourseStatus status,
        LocalDateTime createdAt
) {

    public static CourseResponse fromDomain(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getStatus(),
                course.getCreatedAt()
        );
    }
}
