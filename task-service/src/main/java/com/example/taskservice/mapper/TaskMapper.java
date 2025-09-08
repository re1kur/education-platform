package com.example.taskservice.mapper;

import com.example.dto.PageDto;
import com.example.dto.TaskDto;
import com.example.payload.TaskPayload;
import com.example.payload.TaskUpdatePayload;
import com.example.taskservice.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface TaskMapper {
    TaskDto read(Task task);

    Task write(TaskPayload payload, List<UUID> fileIds);

    Task update(Task task, TaskUpdatePayload payload);

    PageDto<TaskDto> readPage(Page<Task> foundPage);
}
