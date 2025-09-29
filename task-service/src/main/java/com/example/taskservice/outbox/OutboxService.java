package com.example.taskservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.taskservice.entity.TaskAttempt;

import java.util.List;

public interface OutboxService {
    void successTask(TaskAttempt savedAttempt);

    List<OutboxEventDto> readAll();
}
