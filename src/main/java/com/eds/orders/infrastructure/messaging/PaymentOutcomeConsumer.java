package com.eds.orders.infrastructure.messaging;

import com.eds.orders.domain.model.Order;
import com.eds.orders.domain.repository.OrderRepository;
import com.eds.orders.integration.billing.dto.BillingPaymentFailedEvent;
import com.eds.orders.integration.billing.dto.BillingPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;


@Component
@RequiredArgsConstructor
public class PaymentOutcomeConsumer {

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "${app.kafka.billingPaymentSucceededTopic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Transactional
    public void onPaymentSucceeded(String payload) throws Exception {
        BillingPaymentSucceededEvent evt =
                objectMapper.readValue (payload, BillingPaymentSucceededEvent.class);
        Order order = orderRepository.findById(evt.orderId())
                .orElseThrow();
        order.markPaymentSucceeded();
    }

    @KafkaListener(
            topics = "${app.kafka.billingPaymentFailedTopic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Transactional
    public void onPaymentFailed(String payload) throws Exception{
        BillingPaymentFailedEvent evt =
                objectMapper.readValue(payload, BillingPaymentFailedEvent.class);
        Order order = orderRepository.findById(evt.orderId())
                .orElseThrow();
        order.markPaymentFailed(evt.reason());
    }


}
