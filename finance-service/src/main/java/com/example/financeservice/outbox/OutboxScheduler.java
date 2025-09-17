package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.exception.OutboxEventReservedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {
    private final RestTemplate restTemplate;
    private final OutboxEventService service;

    @Value("${custom.catalogue-service.url}")
    private String catalogueServiceEventsUrl;

    private final String CHECK_OUTBOX_EVENTS_MESSAGE = "SCHEDULING. CHECK FOR [{}] EVENTS.";
    private final String EVENTS_ARE_RECEIVED_MESSAGE = "SCHEDULING. OUTBOX EVENTS [{}] HAVE BEEN RECEIVED. LENGTH: [{}]";
    private final String EVENTS_ARE_NULL_MESSAGE = "SCHEDULING. OUTBOX EVENTS ARE NULL.";

    @Scheduled(fixedDelay = 10_000)
    public void getPayOrderRequestOutboxEvents() {
        log.info(CHECK_OUTBOX_EVENTS_MESSAGE, OutboxType.PAY_ORDER_REQUEST);

        ResponseEntity<List<OutboxEventDto>> response = restTemplate.exchange(
                catalogueServiceEventsUrl + "?type=" + OutboxType.PAY_ORDER_REQUEST.name(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<OutboxEventDto> events = response.getBody();

        log.info(EVENTS_ARE_RECEIVED_MESSAGE, OutboxType.PAY_ORDER_REQUEST, events.size());

        for (OutboxEventDto event : events) {
            try {
                service.payOrder(event);
            } catch (OutboxEventReservedException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("END OF SCHEDULING.");
    }

    @Scheduled(fixedDelay = 10_000)
    public void getPerformTransactionOutboxEvents() {
        log.info(CHECK_OUTBOX_EVENTS_MESSAGE, OutboxType.PERFORM_TRANSACTION);

        List<OutboxEvent> events = service.getAll(OutboxType.PERFORM_TRANSACTION);

        log.info(EVENTS_ARE_RECEIVED_MESSAGE, OutboxType.PERFORM_TRANSACTION, events.size());

        for (OutboxEvent event : events) {
            try {
                service.performTransaction(event);
            } catch (OutboxEventReservedException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("END OF SCHEDULING.");
    }
}
