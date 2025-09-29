package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.event.PayOrderRequest;
import com.example.exception.OutboxEventReservedException;
import com.example.financeservice.entity.Transaction;
import com.example.financeservice.service.TransactionService;
import com.example.financeservice.util.WebClient;
import com.example.payload.SuccessTaskPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {
    private final OutboxMapper mapper;
    private final OutboxRepository repo;
    private final WebClient webClient;
    private final TransactionService transactionService;

    @Value("${custom.outbox.reservation-time}")
    public int reservationMinutes;

    @Override
    public void payOrder(OutboxEventDto event) {
        UUID eventId = event.id();
        log.info("LISTEN PAY ORDER EVENT [{}].", eventId);

        reserveOutboxEvent(event.reservedTo(), eventId, OutboxType.PAY_ORDER_REQUEST);

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
    @Transactional(noRollbackFor = {IllegalStateException.class, OutboxEventReservedException.class})
    public void performTransaction(OutboxEvent event) {
        UUID eventId = event.getId();
        log.info("LISTEN PERFORM TRANSACTION EVENT [{}]", eventId);

        reserveFinanceEvent(event);

        UUID transactionId = mapper.performTransaction(event);
        try {
            transactionService.perform(transactionId);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        deleteFinanceEvent(event);

        log.info("PERFORM TRANSACTION EVENT [{}] IS LISTENED.", eventId);
    }

    @Override
    public void successTask(OutboxEventDto event) {
        UUID eventId = event.id();
        log.info("LISTEN SUCCESS TASK EVENT [{}].", eventId);

        reserveOutboxEvent(event.reservedTo(), eventId, OutboxType.SUCCESS_TASK);

        SuccessTaskPayload payload = mapper.successTask(event);
        Transaction saved = transactionService.create(payload);

        createTransaction(saved);

        webClient.deleteEvent(eventId);

        log.info("SUCCESS TASK EVENT [{}] IS LISTENED.", eventId);
    }

    private void deleteFinanceEvent(OutboxEvent event) {
        UUID id = event.getId();
        log.info("DELETE OUTBOX EVENT [{}] REQUEST.", id);

        repo.delete(event);

        log.info("OUTBOX EVENT [{}] IS DELETED.", id);
    }

    private void reserveFinanceEvent(OutboxEvent event) {
        UUID id = event.getId();
        log.info("RESERVE OUTBOX EVENT [{}] REQUEST.", id);

        int reserved = repo.reserve(id, LocalDateTime.now().plusMinutes(1));

        if (reserved == 0) throw new OutboxEventReservedException("OUTBOX EVENT [%s] ALREADY RESERVED.".formatted(id));

        log.info("OUTBOX EVENT [{}] IS RESERVED", id);
    }

    private void reserveOutboxEvent(LocalDateTime reservedTo, UUID id, OutboxType type) {
        if (reservedTo == null || reservedTo.isBefore(LocalDateTime.now())) {
            webClient.reserveOutboxEvent(id, type);
            return;
        }
        throw new OutboxEventReservedException("OUTBOX EVENT [%s] ALREADY RESERVED.".formatted(id));
    }
}
