package com.eds.orders.service;

import com.eds.orders.domain.event.EventPublishStatus;
import com.eds.orders.domain.event.OrderCreatedEvent;
import com.eds.orders.domain.model.Order;
import com.eds.orders.domain.model.OrderItem;
import com.eds.orders.domain.repository.OrderRepository;
import com.eds.orders.infrastructure.messaging.OrderEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderApplicationService(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Order createOrder(List<OrderItem> items) {
        Order order = Order.create(items);
        return orderRepository.save(order);
    }

    public EventPublishStatus publishOrderCreatedEvent(Order order) {

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getTotalAmount(),
                order.getItems().stream()
                        .map(item -> new OrderCreatedEvent.Item(
                                item.getProductId(),
                                item.getQuantity()
                        ))
                        .toList()
        );

        // No transaction is active here — publish directly
        // If Kafka is down, publisher will persist pending_events
        return eventPublisher.publish(event);
    }
}