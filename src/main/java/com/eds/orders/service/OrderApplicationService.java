package com.eds.orders.service;

import com.eds.orders.domain.event.OrderCreatedEvent;
import com.eds.orders.domain.model.Order;
import com.eds.orders.domain.model.OrderItem;
import com.eds.orders.domain.repository.OrderRepository;
import com.eds.orders.infrastructure.messaging.OrderEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
        Order saved = orderRepository.save(order);
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                            eventPublisher.publish(
                                    new OrderCreatedEvent(
                                    saved.getId(),
                                    saved.getTotalAmount(),
                                    saved.getItems().stream()
                                            .map(item -> new OrderCreatedEvent.Item(
                                                    item.getProductId(),
                                                    item.getQuantity()
                                            ))
                                            .toList()

                            )
                        );
                    }
                }
        );
        return orderRepository.save(order);
    }
}