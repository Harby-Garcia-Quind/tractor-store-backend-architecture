package com.onlinecourses.billing.domain.model;

import com.onlinecourses.billing.domain.exception.InvalidPaymentOrderException;
import com.onlinecourses.billing.domain.model.enums.PaymentOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentOrder {

    private final UUID id;
    private final UUID enrollmentId;
    private final BigDecimal amount;
    private PaymentOrderStatus status;
    private final LocalDateTime createdAt;

    private PaymentOrder(
            UUID id,
            UUID enrollmentId,
            BigDecimal amount,
            PaymentOrderStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static PaymentOrder create(UUID enrollmentId, BigDecimal amount) {
        validate(enrollmentId, amount);

        return new PaymentOrder(
                UUID.randomUUID(),
                enrollmentId,
                amount,
                PaymentOrderStatus.PENDING,
                LocalDateTime.now()
        );
    }

    public void pay() {
        if (this.status != PaymentOrderStatus.PENDING) {
            throw new InvalidPaymentOrderException("Solo una orden de pago pendiente puede ser pagada");
        }

        this.status = PaymentOrderStatus.PAID;
    }

    public static PaymentOrder rehydrate(
            UUID id,
            UUID enrollmentId,
            BigDecimal amount,
            PaymentOrderStatus status,
            LocalDateTime createdAt
    ) {
        validate(enrollmentId, amount);

        if (id == null) {
            throw new InvalidPaymentOrderException("El id de la orden de pago no puede ser nulo");
        }

        if (status == null) {
            throw new InvalidPaymentOrderException("El estado de la orden de pago no puede ser nulo");
        }

        if (createdAt == null) {
            throw new InvalidPaymentOrderException("La fecha de creación no puede ser nula");
        }

        return new PaymentOrder(
                id,
                enrollmentId,
                amount,
                status,
                createdAt
        );
    }

    private static void validate(UUID enrollmentId, BigDecimal amount) {
        if (enrollmentId == null) {
            throw new InvalidPaymentOrderException("El id de inscripción no puede ser nulo");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentOrderException("El monto debe ser mayor a cero");
        }
    }

    private static void validatePayOrder(UUID enrollmentId, PaymentOrderStatus status) {


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