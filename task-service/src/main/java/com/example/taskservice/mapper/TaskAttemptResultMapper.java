package com.example.taskservice.mapper;

import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.entity.TaskAttemptResult;

import java.util.UUID;

public interface TaskAttemptResultMapper {
    TaskAttemptResultDto read(TaskAttemptResult result);

    TaskAttemptResult create(TaskAttemptResultPayload payload, UUID userId, TaskAttempt found);
}
