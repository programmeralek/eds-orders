package com.eds.orders.api.dto;

import com.eds.orders.domain.event.EventPublishStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class CreateOrderResponse {

    private final Long orderId;
    private final String status;
    private final BigDecimal totalAmount;
    private final Instant createdAt;
    private final EventPublishStatus eventPublishStatus;

    public CreateOrderResponse(
            Long orderId,
            String status,
            BigDecimal totalAmount,
            Instant createdAt,
            EventPublishStatus eventPublishStatus
    ) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.eventPublishStatus = eventPublishStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public EventPublishStatus getEventPublishStatus() {
        return eventPublishStatus;
    }
}
