package com.eds.orders.service;

import com.eds.orders.domain.model.Order;
import com.eds.orders.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryOutcomeService {
    private final OrderRepository orderRepository;

    public InventoryOutcomeService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void onInventoryReserved(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElse(null);
        if(order == null) return;
        order.markInventoryReserved();
        orderRepository.save(order);
   }

   @Transactional
    public void onInventoryRejected(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElse(null);
        if(order == null) return;
        order.markInventoryRejected();
        orderRepository.save(order);
   }

}
