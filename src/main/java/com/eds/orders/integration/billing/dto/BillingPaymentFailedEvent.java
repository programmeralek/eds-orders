package com.eds.orders.integration.billing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BillingPaymentFailedEvent(
        @JsonProperty("EventId") UUID eventId,
        @JsonProperty("OrderId") Long orderId,
        @JsonProperty("Amount") BigDecimal amount,
        @JsonProperty("Reason") String reason,
        @JsonProperty("OccurredAt") Instant occurredAt
) {
}
