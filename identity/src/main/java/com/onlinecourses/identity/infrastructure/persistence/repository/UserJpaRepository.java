package com.onlinecourses.identity.infrastructure.persistence.repository;

import com.onlinecourses.identity.domain.model.enums.UserStatus;
import com.onlinecourses.identity.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

    boolean existsByEmail(String email);
    boolean existsByIdAndStatus(UUID id, UserStatus status);

}
