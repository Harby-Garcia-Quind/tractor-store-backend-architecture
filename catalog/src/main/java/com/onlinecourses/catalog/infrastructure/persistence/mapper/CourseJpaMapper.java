package com.onlinecourses.catalog.infrastructure.persistence.mapper;

import com.onlinecourses.catalog.domain.model.Course;
import com.onlinecourses.catalog.infrastructure.persistence.entity.CourseJpaEntity;

public class CourseJpaMapper {

    private CourseJpaMapper() {}

    public static CourseJpaEntity toEntity(Course course) {
        return new CourseJpaEntity(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getStatus(),
                course.getCreatedAt()
        );
    }

    public static Course toDomain(CourseJpaEntity entity) {
        return Course.rehydrate(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getCreatedAt()
        );

    }
}
