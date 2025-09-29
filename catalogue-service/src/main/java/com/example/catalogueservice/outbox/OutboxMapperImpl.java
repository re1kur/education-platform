package com.example.catalogueservice.outbox;

import com.example.catalogueservice.entity.Order;
import com.example.catalogueservice.entity.Product;
import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.event.OutboxEventPayload;
import com.example.event.PayOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OutboxMapperImpl implements OutboxMapper {
    private final ObjectMapper mapper;

    @Value("${custom.outbox.reservation-time}")
    public int reservationMinutes;

    @SneakyThrows
    @Override
    public OutboxEvent create(OutboxEventPayload object, OutboxType type) {
        return OutboxEvent.builder()
                .payload(mapper.writeValueAsString(object))
                .type(type)
                .build();
    }

    @Override
    public OutboxEventDto read(OutboxEvent event) {
        return OutboxEventDto.builder()
                .id(event.getId())
                .createdAt(event.getCreatedAt())
                .payload(event.getPayload())
                .reservedTo(event.getReservedTo())
                .type(event.getType())
                .build();
    }

    @Override
    public OutboxEvent reserve(OutboxEvent event) {
        event.setReservedTo(LocalDateTime.now().plusMinutes(reservationMinutes));

        return event;
    }

    @Override
    public PayOrderRequest payOrder(Order order) {
        return PayOrderRequest.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getProducts().stream().map(Product::getPrice).mapToInt(Integer::intValue).sum())
                .build();
    }

    @Override
    public OutboxEvent release(OutboxEvent event) {
        event.setReservedTo(null);

        return event;
    }
}
