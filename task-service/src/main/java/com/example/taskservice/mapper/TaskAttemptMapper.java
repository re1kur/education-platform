package com.example.taskservice.mapper;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptFullDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptPayload;
import com.example.taskservice.entity.Task;
import com.example.taskservice.entity.TaskAttempt;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface TaskAttemptMapper {
    TaskAttempt create(TaskAttemptPayload payload, Task task, UUID userId, List<UUID> fileIds);

    TaskAttemptDto read(TaskAttempt saved);

    PageDto<TaskAttemptDto> readPage(Page<TaskAttempt> page);

    TaskAttemptFullDto readFull(TaskAttempt found, TaskAttemptResultDto resultDto);
}
