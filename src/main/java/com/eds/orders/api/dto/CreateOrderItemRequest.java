package com.eds.orders.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateOrderItemRequest {

    private String productId;
    @Positive(message = "quantity must be greater than 0")
    private int quantity;
    @DecimalMin(value = "0.00", inclusive = false, message = "price must be greater than 0")
    private BigDecimal price;

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
