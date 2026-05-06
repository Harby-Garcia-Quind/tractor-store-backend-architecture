CREATE SCHEMA IF NOT EXISTS billing;

CREATE TABLE IF NOT EXISTS billing.payment_orders (
        id UUID PRIMARY KEY,
        enrollment_id UUID NOT NULL,
        amount NUMERIC(12, 2) NOT NULL,
        status VARCHAR(30) NOT NULL,
        created_at TIMESTAMP NOT NULL,
        CONSTRAINT uk_billing_payment_order_pending_enrollment UNIQUE (enrollment_id, status)
);