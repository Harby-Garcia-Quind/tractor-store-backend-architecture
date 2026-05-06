package com.onlinecourses.billing.infrastructure.repository;

import com.onlinecourses.billing.application.port.PaymentOrderRepository;
import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;
import com.onlinecourses.billing.infrastructure.persistence.entity.PaymentOrderJpaEntity;
import com.onlinecourses.billing.infrastructure.persistence.mapper.PaymentOderJpaMapper;
import com.onlinecourses.billing.infrastructure.persistence.repository.PaymentOrderJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Profile("postgres")
public class PostgresPaymentOrderRepository implements PaymentOrderRepository {

    private final PaymentOrderJpaRepository paymentOrderJpaRepository;

    public PostgresPaymentOrderRepository(PaymentOrderJpaRepository paymentOrderJpaRepository) {
        this.paymentOrderJpaRepository = paymentOrderJpaRepository;
    }

    @Override
    public boolean existsPendingByEnrollmentId(UUID enrollmentId) {
        return paymentOrderJpaRepository.existsByEnrollmentIdAndStatus(enrollmentId, PaymentOrderStatus.PENDING);
    }

    @Override
    public PaymentOrder save(PaymentOrder paymentOrder) {
        PaymentOrderJpaEntity entity = PaymentOderJpaMapper.toEntity(paymentOrder);

        System.out.println(paymentOrder.getId());

        PaymentOrderJpaEntity savedEntity = paymentOrderJpaRepository.save(entity);

        return PaymentOderJpaMapper.toDomain(savedEntity);

    }
}
