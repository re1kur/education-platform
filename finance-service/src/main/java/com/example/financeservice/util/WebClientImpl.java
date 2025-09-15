package com.example.financeservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientImpl implements WebClient {
    private final RestTemplate restTemplate;

    @Value("${custom.catalogue-service.url}")
    private String URL_OUTBOX_CATALOGUE;

    @Override
    public void reserveCatalogueOutboxEvent(UUID eventId) {
        log.info("RESERVE OUTBOX EVENT [{}] REQUEST.", eventId);

        String url = URL_OUTBOX_CATALOGUE + "/%s/reserve".formatted(eventId);
        restTemplate.put(url, Void.class);

        log.info("OUTBOX EVENT [{}] IS RESERVED.", eventId);
    }

    @Override
    public void deleteEvent(UUID eventId) {
        String url = URL_OUTBOX_CATALOGUE + "/%s".formatted(eventId);
        log.info("DELETE OUTBOX EVENT [{}] REQUEST.", eventId);

        restTemplate.delete(url);

        log.info("OUTBOX EVENT [{}] IS DELETED.", eventId);
    }

    @Override
    public void releaseCatalogueOutboxEvent(UUID eventId) {
        log.info("RELEASE OUTBOX EVENT [{}] REQUEST.", eventId);

        String url = URL_OUTBOX_CATALOGUE + "/%s/release".formatted(eventId);
        restTemplate.put(url, Void.class);

        log.info("OUTBOX EVENT [{}] IS RELEASED.", eventId);
    }
}
