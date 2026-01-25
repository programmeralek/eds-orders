package com.eds.orders.infrastructure.messaging;

import com.eds.orders.integration.inventory.dto.InventoryReservationFailedEvent;
import com.eds.orders.integration.inventory.dto.InventoryReservationSucceededEvent;
import com.eds.orders.service.InventoryOutcomeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class InventoryOutcomeConsumer {
    private final ObjectMapper objectMapper;
    private final InventoryOutcomeService outcomeService;

    public InventoryOutcomeConsumer(ObjectMapper objectMapper, InventoryOutcomeService inventoryOutcomeService) {
        this.objectMapper = objectMapper;
        this.outcomeService = inventoryOutcomeService;
    }

    @KafkaListener(topics = "${app.kafka.inventoryReservationSucceededTopic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onInventoryReservationSucceeded(ConsumerRecord<String, String> record, Acknowledgment ack){
        InventoryReservationSucceededEvent evt = objectMapper.readValue(record.value(), InventoryReservationSucceededEvent.class);
        if(evt.orderId() != null){
            outcomeService.onInventoryReserved(evt.orderId());
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "${app.kafka.inventoryReservationFailedTopic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onInventoryReservationFailed(ConsumerRecord<String, String> record, Acknowledgment ack){
        InventoryReservationFailedEvent evt = objectMapper.readValue(record.value(), InventoryReservationFailedEvent.class);
        if(evt.orderId() != null){
            outcomeService.onInventoryRejected(evt.orderId());
        }
        ack.acknowledge();
    }

}
