package com.example.financeservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.event.PayOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxEventMapperImpl implements OutboxEventMapper {
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public PayOrderRequest payOrderRequest(OutboxEventDto event) {
        return objectMapper.readValue(event.payload(), PayOrderRequest.class);
    }

    @Override
    public OutboxEvent transaction(UUID transactionId) {
        return OutboxEvent.builder()
                .type(OutboxType.PERFORM_TRANSACTION)
                .payload(transactionId.toString())
                .build();
    }

    @Override
    public UUID performTransaction(OutboxEvent event) {
        return event.getId();
    }
}
