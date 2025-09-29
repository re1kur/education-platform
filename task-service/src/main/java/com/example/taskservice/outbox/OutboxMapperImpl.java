package com.example.taskservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import com.example.payload.SuccessTaskPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMapperImpl implements OutboxMapper {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public OutboxEvent successTask(TaskAttempt attempt) {
        String payload = objectMapper.writeValueAsString(
                SuccessTaskPayload.builder()
                        .userId(attempt.getUserId())
                        .cost(attempt.getTask().getCost())
                        .build());
        return OutboxEvent.builder()
                .type(OutboxType.SUCCESS_TASK)
                .payload(payload)
                .build();
    }

    @Override
    public OutboxEventDto read(OutboxEvent event) {
        return OutboxEventDto.builder()
                .id(event.getId())
                .type(event.getType())
                .createdAt(event.getCreatedAt())
                .reservedTo(event.getReservedTo())
                .payload(event.getPayload())
                .build();
    }
}
