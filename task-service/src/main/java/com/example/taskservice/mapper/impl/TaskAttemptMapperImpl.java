package com.example.taskservice.mapper.impl;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptFullDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptPayload;
import com.example.taskservice.entity.Task;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.mapper.TaskAttemptMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TaskAttemptMapperImpl implements TaskAttemptMapper {
    @Override
    public TaskAttempt create(TaskAttemptPayload payload, Task task, UUID userId, List<UUID> fileIds) {
        TaskAttempt build = TaskAttempt.builder()
                .comment(payload.comment())
                .task(task)
                .userId(userId)
                .build();

        if (fileIds != null) build.setFileIds(fileIds);

        return build;
    }

    @Override
    public TaskAttemptDto read(TaskAttempt saved) {
        return TaskAttemptDto.builder()
                .id(saved.getId())
                .taskId(saved.getTask().getId())
                .comment(saved.getComment())
                .status(saved.getStatus().name())
                .userId(saved.getUserId())
                .fileIds(saved.getFileIds())
                .build();
    }

    @Override
    public PageDto<TaskAttemptDto> readPage(Page<TaskAttempt> page) {
        List<TaskAttemptDto> content = page.stream().map(this::read).toList();
        return new PageDto<>(content, page.getNumber(), page.getSize(), page.getTotalPages(), page.hasNext(), page.hasPrevious());
    }

    @Override
    public TaskAttemptFullDto readFull(TaskAttempt found, TaskAttemptResultDto resultDto) {
        return TaskAttemptFullDto.builder()
                .id(found.getId())
                .userId(found.getUserId())
                .taskId(found.getTask().getId())
                .comment(found.getComment())
                .status(found.getStatus().name())
                .fileIds(found.getFileIds())
                .result(resultDto)
                .build();
    }
}
