package com.eds.orders.integration.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record InventoryReservationFailedEvent(
        @JsonProperty("EventId") UUID eventId,
        @JsonProperty("OrderId") Long orderId,
        @JsonProperty("Reason") String reason,
        @JsonProperty("OccurredAt") Instant occurredAt,
        @JsonProperty("Items") List<InventoryItem> items
) {}