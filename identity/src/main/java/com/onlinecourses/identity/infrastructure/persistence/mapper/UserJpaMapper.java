package com.onlinecourses.identity.infrastructure.persistence.mapper;

import com.onlinecourses.identity.domain.model.User;
import com.onlinecourses.identity.infrastructure.persistence.entity.UserJpaEntity;

public class UserJpaMapper {
    private UserJpaMapper() {}

    public static UserJpaEntity ToEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getFullName(),
                user.getEmail().value(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }

    public static User toDomain(UserJpaEntity entity) {
        return User.rehydrate(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
