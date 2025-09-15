package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.event.PayOrderRequest;

import java.util.UUID;

public interface OutboxEventMapper {
    PayOrderRequest payOrderRequest(OutboxEventDto event);

    OutboxEvent transaction(UUID transactionId);

    UUID performTransaction(OutboxEvent event);
}
