package com.onlinecourses.billing.infrastructure.persistence.entity;

import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_orders", schema = "billing")
public class PaymentOrderJpaEntity {

    @Id
    private UUID id;
    @Column(name = "enrollment_id", nullable = false)
    private UUID enrollmentId;
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentOrderStatus status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected PaymentOrderJpaEntity() {
    }

    public PaymentOrderJpaEntity(UUID id, UUID enrollmentId, BigDecimal amount, PaymentOrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
