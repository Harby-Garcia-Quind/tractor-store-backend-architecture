package com.onlinecourses.billing.infrastructure.persistence.mapper;
import com.onlinecourses.billing.domain.model.PaymentOrder;
import com.onlinecourses.billing.infrastructure.persistence.entity.PaymentOrderJpaEntity;

public class PaymentOderJpaMapper {
    private PaymentOderJpaMapper() {}

    public static PaymentOrderJpaEntity toEntity(PaymentOrder paymentOrder) {
        return new PaymentOrderJpaEntity(
                paymentOrder.getId(),
                paymentOrder.getEnrollmentId(),
                paymentOrder.getAmount(),
                paymentOrder.getStatus(),
                paymentOrder.getCreatedAt()
        );
    }

    public static PaymentOrder toDomain(PaymentOrderJpaEntity entity) {
        return PaymentOrder.rehydrate(
                entity.getId(),
                entity.getEnrollmentId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
