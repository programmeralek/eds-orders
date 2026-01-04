package com.eds.orders.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class CreateOrderResponse {

    private final Long orderId;
    private final String status;
    private final BigDecimal totalAmount;
    private final Instant createdAt;

    public CreateOrderResponse(
            Long orderId,
            String status,
            BigDecimal totalAmount,
            Instant createdAt
    ) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
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
}
