package com.example.catalogueservice.outbox;

import com.example.catalogueservice.entity.Order;
import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;

import java.util.List;
import java.util.UUID;

public interface OutboxEventService {
    void createOrder(Order payload);

    List<OutboxEventDto> readAll(OutboxType type);

    void reserve(UUID id);

    void release(UUID id);
}
