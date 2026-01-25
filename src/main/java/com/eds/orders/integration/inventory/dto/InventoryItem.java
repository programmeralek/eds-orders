package com.eds.orders.integration.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InventoryItem(
        @JsonProperty("ProductId") String productId,
        @JsonProperty("Quantity") int quantity
) { }
