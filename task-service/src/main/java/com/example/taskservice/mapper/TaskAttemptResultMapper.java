package com.example.taskservice.mapper;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.entity.TaskAttemptResult;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TaskAttemptResultMapper {
    TaskAttemptResultDto read(TaskAttemptResult result);

    TaskAttemptResult create(TaskAttemptResultPayload payload, UUID userId, TaskAttempt found);

    PageDto<TaskAttemptResultDto> readPage(Page<TaskAttemptResult> results);
}
