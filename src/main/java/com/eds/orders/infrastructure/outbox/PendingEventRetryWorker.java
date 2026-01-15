package com.eds.orders.infrastructure.outbox;

import com.eds.orders.domain.event.EventPublishStatus;
import com.eds.orders.domain.event.OrderCreatedEvent;
import com.eds.orders.infrastructure.messaging.OrderEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class PendingEventRetryWorker {
    private final PendingEventRepository pendingEventRepository;
    private final OrderEventPublisher eventPublisher;
    private ObjectMapper objectMapper = new ObjectMapper();

    public PendingEventRetryWorker(PendingEventRepository pendingEventRepository, OrderEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.pendingEventRepository = pendingEventRepository;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void retryPendingEvents() {
        List<PendingEvent> events = pendingEventRepository.findAll();

        for (PendingEvent pe : events) {
            try {
                OrderCreatedEvent event =
                        objectMapper.readValue(pe.getPayload(), OrderCreatedEvent.class);

                EventPublishStatus status = eventPublisher.publish(event);

                if (status == EventPublishStatus.PUBLISHED) {
                    pendingEventRepository.delete(pe);
                }
            } catch (Exception ex) {
                // log and leave for next retry
            }
        }
    }

}
