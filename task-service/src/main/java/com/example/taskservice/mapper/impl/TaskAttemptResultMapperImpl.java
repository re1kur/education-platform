package com.example.taskservice.mapper.impl;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.entity.TaskAttemptResult;
import com.example.taskservice.mapper.TaskAttemptResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Override
    public PageDto<TaskAttemptResultDto> readPage(Page<TaskAttemptResult> page) {
        List<TaskAttemptResultDto> content = page.stream().map(this::read).toList();
        return new PageDto<>(content, page.getNumber(), page.getSize(), page.getTotalPages(), page.hasNext(), page.hasPrevious());
    }
}
