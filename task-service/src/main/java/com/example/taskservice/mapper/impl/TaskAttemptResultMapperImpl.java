package com.example.taskservice.mapper.impl;

import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.entity.TaskAttemptResult;
import com.example.taskservice.mapper.TaskAttemptResultMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskAttemptResultMapperImpl implements TaskAttemptResultMapper {
    @Override
    public TaskAttemptResultDto read(TaskAttemptResult result) {
        if (result == null) return null;

        return TaskAttemptResultDto.builder()
                .checkedBy(result.getCheckedBy())
                .checkedAt(result.getCheckedAt())
                .comment(result.getComment())
                .build();
    }

    @Override
    public TaskAttemptResult create(TaskAttemptResultPayload payload, UUID userId, TaskAttempt taskAttempt) {
        return TaskAttemptResult.builder()
                .taskAttempt(taskAttempt)
                .checkedBy(userId)
                .comment(payload.comment())
                .build();
    }
}
