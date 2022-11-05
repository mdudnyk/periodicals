package com.periodicals.entity;

import com.periodicals.entity.enums.PaymentStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment implements Serializable {
    private final String id;
    private final int userId;
    private final int amount;
    private PaymentStatus status;
    private final LocalDateTime createdAt;

    public Payment(final String id, final int userId, final int amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        status = PaymentStatus.NOT_COMPLETED;
        this.createdAt = LocalDateTime.now();
    }

    public Payment(final String id, final int userId, final int amount,
                   final PaymentStatus status, final LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(final PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Payment payment = (Payment) o;
        return userId == payment.userId && amount == payment.amount
                && id.equals(payment.id) && status == payment.status
                && createdAt.equals(payment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, amount, status, createdAt);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}