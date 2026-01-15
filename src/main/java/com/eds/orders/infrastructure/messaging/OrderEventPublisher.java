package com.eds.orders.infrastructure.messaging;

import com.eds.orders.domain.event.EventPublishStatus;
import com.eds.orders.domain.event.OrderCreatedEvent;
import com.eds.orders.infrastructure.outbox.PendingEvent;
import com.eds.orders.infrastructure.outbox.PendingEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private static final String TOPIC = "order.created";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final PendingEventRepository pendingEventRepository;
    private final ObjectMapper objectMapper;

    public OrderEventPublisher(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
            PendingEventRepository pendingEventRepository,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.pendingEventRepository = pendingEventRepository;
        this.objectMapper = objectMapper;
    }

    public EventPublishStatus publish(OrderCreatedEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event.getOrderId().toString(), event);
            return EventPublishStatus.PUBLISHED;
        } catch (Exception ex) {
            // Kafka unavailable / broker down / serialization issues / etc.
            pendingEventRepository.save(
                    new PendingEvent(
                            "Order",
                            event.getOrderId(),
                            "OrderCreated",
                            serialize(event)
                    )
            );
            return EventPublishStatus.PENDING_RETRY;
        }
    }

    private String serialize(OrderCreatedEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            // Worst case: still persist something durable
            return "{\"error\":\"failed_to_serialize_event\",\"eventType\":\"OrderCreated\",\"orderId\":\"" + event.getOrderId() + "\"}";
        }
    }

//    public EventPublishStatus publishRaw(
//            String eventType,
//            Long aggregateId,
//            String payload
//    ) {
//        try {
//            rawKafkaTemplate.send(
//                    eventType,
//                    aggregateId.toString(),
//                    payload
//            );
//            return EventPublishStatus.PUBLISHED;
//        } catch (Exception ex) {
//            return EventPublishStatus.PENDING_RETRY;
//        }
//    }

}
