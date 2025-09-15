package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.event.PayOrderRequest;
import com.example.exception.OutboxEventReservedException;
import com.example.financeservice.entity.Transaction;
import com.example.financeservice.service.TransactionService;
import com.example.financeservice.util.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {
    private final OutboxEventMapper mapper;
    private final OutboxEventRepository repo;
    private final WebClient webClient;
    private final TransactionService transactionService;

    @Override
    public void payOrder(OutboxEventDto event) {
        UUID eventId = event.id();
        log.info("LISTEN PAY ORDER EVENT [{}].", eventId);

        reserveCatalogueEvent(event.reservedTo(), eventId);

        PayOrderRequest request = mapper.payOrderRequest(event);
        Transaction saved = transactionService.create(request);

        createTransaction(saved);


        webClient.deleteEvent(eventId);

        log.info("PAY ORDER EVENT [{}] IS LISTENED.", eventId);
    }

    @Override
    public void createTransaction(Transaction transaction) {
        UUID transactionId = transaction.getId();
        log.info("CREATE OUTBOX EVENT [{}] BY TRANSACTION [{}].", OutboxType.PERFORM_TRANSACTION, transactionId);

        OutboxEvent mapped = mapper.transaction(transactionId);
        OutboxEvent saved = repo.save(mapped);

        log.info("OUTBOX EVENT [{}] IS CREATED.", saved.getId());
    }

    @Override
    public List<OutboxEvent> getAll(OutboxType outboxType) {
        List<OutboxEvent> events = repo.findAll(outboxType);
        if (events == null) return List.of();
        return events;
    }

    @Override
    public void performTransaction(OutboxEvent event) {
        UUID eventId = event.getId();
        log.info("LISTEN PERFORM TRANSACTION EVENT [{}]", eventId);

        reserveCatalogueEvent(event.getReservedTo(), eventId);

        UUID transactionId = mapper.performTransaction(event);
        try {
            transactionService.perform(transactionId);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            webClient.releaseCatalogueOutboxEvent(eventId);
            return;
        }

        webClient.deleteEvent(eventId);

        log.info("PERFORM TRANSACTION EVENT [{}] IS LISTENED.", eventId);
    }

    private void reserveCatalogueEvent(LocalDateTime reservedTo, UUID id) {
        if (reservedTo.isBefore(LocalDateTime.now())) {
            webClient.reserveCatalogueOutboxEvent(id);
            return;
        }
        throw new OutboxEventReservedException("OUTBOX EVENT [%s] ALREADY RESERVED.".formatted(id));
    }
}
