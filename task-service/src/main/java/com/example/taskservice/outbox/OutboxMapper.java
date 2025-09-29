package com.example.taskservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.taskservice.entity.TaskAttempt;

public interface OutboxMapper {
    OutboxEvent successTask(TaskAttempt savedAttempt);

    OutboxEventDto read(OutboxEvent outboxEvent);
}
