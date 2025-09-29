package com.example.catalogueservice.outbox;

import com.example.catalogueservice.entity.Order;
import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.event.OutboxEventPayload;
import com.example.event.PayOrderRequest;

public interface OutboxMapper {
    OutboxEvent create(OutboxEventPayload payload, OutboxType type);

    OutboxEventDto read(OutboxEvent outboxEvent);

    OutboxEvent reserve(OutboxEvent found);

    PayOrderRequest payOrder(Order order);

    OutboxEvent release(OutboxEvent found);
}
