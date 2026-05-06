package com.onlinecourses.billing.infrastructure.persistence.repository;

import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;
import com.onlinecourses.billing.infrastructure.persistence.entity.PaymentOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderJpaEntity, UUID> {

    boolean existsByEnrollmentIdAndStatus(UUID enrollmentId, PaymentOrderStatus status);

}
