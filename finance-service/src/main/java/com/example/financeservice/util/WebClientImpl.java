package com.example.financeservice.util;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientImpl implements WebClient {
    private final RestTemplate restTemplate;

    @Value("${custom.catalogue-service.url}")
    private String urlOutboxCatalogue;

    @Value("${custom.task-service.url}")
    private String urlOutboxTask;

    @Override
    public void reserveOutboxEvent(UUID eventId, OutboxType type) {
        log.info("RESERVE OUTBOX EVENT [{}] TYPE [{}] REQUEST.", eventId, type.name());

        String urlSource;

        if (type.equals(OutboxType.PAY_ORDER_REQUEST)) {
            urlSource = urlOutboxCatalogue;
        } else if (type.equals(OutboxType.SUCCESS_TASK)) {
            urlSource = urlOutboxTask;
        } else {
            throw new IllegalArgumentException("INVALID OUTBOX EVENT [%s].".formatted(type.name()));
        }

        String url = urlSource + "/%s/reserve".formatted(eventId);
        restTemplate.put(url, Void.class);

        log.info("OUTBOX EVENT [{}] IS RESERVED.", eventId);
    }

    @Override
    public void deleteEvent(UUID eventId) {
        String url = urlOutboxCatalogue + "/%s".formatted(eventId);
        log.info("DELETE OUTBOX EVENT [{}] REQUEST.", eventId);

        restTemplate.delete(url);

        log.info("OUTBOX EVENT [{}] IS DELETED.", eventId);
    }

    @Override
    public void releaseCatalogueOutboxEvent(UUID eventId) {
        log.info("RELEASE OUTBOX EVENT [{}] REQUEST.", eventId);

        String url = urlOutboxCatalogue + "/%s/release".formatted(eventId);
        restTemplate.put(url, Void.class);

        log.info("OUTBOX EVENT [{}] IS RELEASED.", eventId);
    }

    @Override
    public List<OutboxEventDto> getOutboxEvents(OutboxType type) {
        String urlSource;

        if (type.equals(OutboxType.PAY_ORDER_REQUEST)) {
            urlSource = urlOutboxCatalogue;
        } else if (type.equals(OutboxType.SUCCESS_TASK)) {
            urlSource = urlOutboxTask;
        } else {
            throw new IllegalArgumentException("INVALID OUTBOX EVENT [%s].".formatted(type.name()));
        }

        ResponseEntity<List<OutboxEventDto>> response = restTemplate.exchange(
                urlSource + "?type=" + type.name(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OutboxEventDto>>() {
                }
        );

        return response.getBody();
    }

}
