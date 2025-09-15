package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.financeservice.entity.Transaction;

import java.util.List;

public interface OutboxEventService {
    void payOrder(OutboxEventDto outboxEventDto);

    void createTransaction(Transaction saved);

    List<OutboxEvent> getAll(OutboxType outboxType);

    void performTransaction(OutboxEvent event);
}
