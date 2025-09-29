package com.example.financeservice.util;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;

import java.util.List;
import java.util.UUID;

public interface WebClient {
    void reserveOutboxEvent(UUID id, OutboxType type);

    void deleteEvent(UUID eventId);

    void releaseCatalogueOutboxEvent(UUID eventId);

    List<OutboxEventDto> getOutboxEvents(OutboxType type);
}
