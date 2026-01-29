package com.eds.orders.domain.model;

public enum OrderStatus {
    CREATED,
    INVENTORY_RESERVED,
    INVENTORY_REJECTED,
    PAYMENT_FAILED,
    PAID
}
