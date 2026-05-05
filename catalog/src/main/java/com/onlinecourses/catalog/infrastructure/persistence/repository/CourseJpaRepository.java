package com.onlinecourses.catalog.infrastructure.persistence.repository;

import com.onlinecourses.catalog.domain.model.enums.CourseStatus;
import com.onlinecourses.catalog.infrastructure.persistence.entity.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, UUID> {

    boolean existsByIdAndStatus(UUID id, CourseStatus status);

    boolean existsByTitle(String title);

}
