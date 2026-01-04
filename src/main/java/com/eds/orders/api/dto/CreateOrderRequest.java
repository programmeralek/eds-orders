package com.eds.orders.api.dto;

import java.util.List;

public class CreateOrderRequest {

    private List<CreateOrderItemRequest> items;

    public List<CreateOrderItemRequest> getItems() {
        return items;
    }
}
