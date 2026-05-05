package com.onlinecourses.identity.infrastructure.repository;

import com.onlinecourses.identity.application.port.UserRepository;
import com.onlinecourses.identity.domain.model.Email;
import com.onlinecourses.identity.domain.model.User;
import com.onlinecourses.identity.domain.model.enums.UserStatus;
import com.onlinecourses.identity.infrastructure.persistence.entity.UserJpaEntity;
import com.onlinecourses.identity.infrastructure.persistence.mapper.UserJpaMapper;
import com.onlinecourses.identity.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Profile("postgres")
public class PostgresUserRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public PostgresUserRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public boolean existsByEmail(String email) {
        String normalizedEmail = new Email(email).value();
        return userJpaRepository.existsByEmail(normalizedEmail);
    }

    @Override
    public boolean existsActiveUserById(UUID userId) {
        return userJpaRepository.existsByIdAndStatus(userId, UserStatus.ACTIVE);
    }

    @Override
    public boolean existsById(UUID userId) {
        return userJpaRepository.existsById(userId);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = UserJpaMapper.toEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(entity);

        return UserJpaMapper.toDomain(savedEntity);
    }
}
