package com.eds.orders.domain.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderCreatedEvent {
    private final UUID eventId;
    private final Long orderId;
    private final BigDecimal totalAmount;
    private final List<Item> items;
    private final Instant occurredAt;

    public OrderCreatedEvent(
            Long orderId,
            BigDecimal totalAmount,
            List<Item> items
    ) {
        this.eventId = UUID.randomUUID();
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.items = items;
        this.occurredAt = Instant.now();
    }

    public UUID getEventId() {
        return eventId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<Item> getItems() {
        return items;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public static class Item {
        private final String productId;
        private final int quantity;

        public Item(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
