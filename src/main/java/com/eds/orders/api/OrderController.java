package com.eds.orders.api;

import com.eds.orders.api.dto.CreateOrderItemRequest;
import com.eds.orders.api.dto.CreateOrderRequest;
import com.eds.orders.api.dto.CreateOrderResponse;
import com.eds.orders.domain.model.Order;
import com.eds.orders.domain.model.OrderItem;
import com.eds.orders.service.OrderApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.eds.orders.domain.event.EventPublishStatus;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {

        List<OrderItem> items = request.getItems().stream()
                .map(this::toDomainItem)
                .toList();

        Order order = orderApplicationService.createOrder(items);

        EventPublishStatus publishStatus = orderApplicationService.publishOrderCreatedEvent(order);

        return new CreateOrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                publishStatus
        );
    }

    private OrderItem toDomainItem(CreateOrderItemRequest dto) {
        return new OrderItem(
                dto.getProductId(),
                dto.getQuantity(),
                dto.getPrice()
        );
    }
}
