package com.eds.orders.api.dto;

import java.math.BigDecimal;

public class CreateOrderItemRequest {

    private String productId;
    private int quantity;
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
