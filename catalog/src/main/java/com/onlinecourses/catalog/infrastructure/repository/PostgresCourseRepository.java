package com.onlinecourses.catalog.infrastructure.repository;

import com.onlinecourses.catalog.application.port.CourseRepository;
import com.onlinecourses.catalog.domain.model.Course;
import com.onlinecourses.catalog.domain.model.enums.CourseStatus;
import com.onlinecourses.catalog.infrastructure.persistence.entity.CourseJpaEntity;
import com.onlinecourses.catalog.infrastructure.persistence.mapper.CourseJpaMapper;
import com.onlinecourses.catalog.infrastructure.persistence.repository.CourseJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Profile("postgres")
public class PostgresCourseRepository implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    public PostgresCourseRepository(CourseJpaRepository courseJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
    }


    @Override
    public boolean existsByTitle(String title) {
        return courseJpaRepository.existsByTitle(title);
    }

    @Override
    public boolean existsById(UUID courseId) {
        return courseJpaRepository.existsById(courseId);
    }

    @Override
    public boolean existsActiveCourseById(UUID courseId) {
        return courseJpaRepository.existsByIdAndStatus(courseId, CourseStatus.ACTIVE);
    }

    @Override
    public Course save(Course course) {
        CourseJpaEntity entity = CourseJpaMapper.toEntity(course);
        CourseJpaEntity savedEntity = courseJpaRepository.save(entity);

        return CourseJpaMapper.toDomain(savedEntity);
    }
}
