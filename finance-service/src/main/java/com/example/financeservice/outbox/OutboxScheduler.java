package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.exception.OutboxEventReservedException;
import com.example.financeservice.util.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {
    private final WebClient webClient;
    private final OutboxService service;

    private final String checkOutboxEventsMessage = "SCHEDULING. CHECK FOR [{}] EVENTS.";
    private final String eventsAreReceivedMessage = "SCHEDULING. OUTBOX EVENTS [{}] HAVE BEEN RECEIVED. LENGTH: [{}]";

    @Scheduled(fixedDelay = 10_000)
    public void getPayOrderRequestOutboxEvents() {
        OutboxType type = OutboxType.PAY_ORDER_REQUEST;
        log.info(checkOutboxEventsMessage, type);

        List<OutboxEventDto> events = webClient.getOutboxEvents(type);

        log.info(eventsAreReceivedMessage, type, events.size());

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
        OutboxType type = OutboxType.PERFORM_TRANSACTION;
        log.info(checkOutboxEventsMessage, type);

        List<OutboxEvent> events = service.getAll(type);

        log.info(eventsAreReceivedMessage, type, events.size());

        for (OutboxEvent event : events) {
            try {
                service.performTransaction(event);
            } catch (OutboxEventReservedException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("END OF SCHEDULING.");
    }

    @Scheduled(fixedDelay = 10_000)
    public void getSuccessTaskEvents() {
        OutboxType type = OutboxType.SUCCESS_TASK;
        log.info(checkOutboxEventsMessage, type);

        List<OutboxEventDto> events = webClient.getOutboxEvents(type);

        log.info(eventsAreReceivedMessage, type, events.size());

        for (OutboxEventDto event : events) {
            try {
                service.successTask(event);
            } catch (OutboxEventReservedException e) {
                log.warn(e.getMessage());
            }
        }
        log.info("END OF SCHEDULING.");
    }
}
