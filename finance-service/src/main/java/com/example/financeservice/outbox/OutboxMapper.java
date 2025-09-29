package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.event.PayOrderRequest;
import com.example.payload.SuccessTaskPayload;

import java.util.UUID;

public interface OutboxMapper {
    PayOrderRequest payOrderRequest(OutboxEventDto event);

    OutboxEvent transaction(UUID transactionId);

    UUID performTransaction(OutboxEvent event);

    SuccessTaskPayload successTask(OutboxEventDto event);
}
