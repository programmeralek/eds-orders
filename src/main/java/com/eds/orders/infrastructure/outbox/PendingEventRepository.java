package com.eds.orders.infrastructure.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PendingEventRepository  extends JpaRepository<PendingEvent, UUID> {
}
