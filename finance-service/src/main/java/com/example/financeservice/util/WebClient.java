package com.example.financeservice.util;

import java.util.UUID;

public interface WebClient {
    void reserveCatalogueOutboxEvent(UUID id);

    void deleteEvent(UUID eventId);

    void releaseCatalogueOutboxEvent(UUID eventId);
}
