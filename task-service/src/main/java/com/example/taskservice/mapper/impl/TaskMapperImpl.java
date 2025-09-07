package com.example.taskservice.mapper.impl;

import com.example.dto.TaskDto;
import com.example.payload.TaskPayload;
import com.example.payload.TaskUpdatePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.taskservice.entity.Task;
import com.example.taskservice.mapper.TaskMapper;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto read(Task task) {
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getPreviewDescription(),
                task.getCost()
        );
    }

    @Override
    public Task write(TaskPayload payload, List<UUID> fileIds) {
        Task build = Task.builder()
                .name(payload.name())
                .description(payload.description())
                .previewDescription(payload.previewDescription())
                .cost(payload.cost())
                .build();
        if (fileIds != null) build.setFileIds(fileIds);

        return build;
    }

    @Override
    public Task update(Task task, TaskUpdatePayload payload){
        task.setName(payload.name());
        task.setDescription(payload.description());
        task.setPreviewDescription(payload.previewDescription());
        task.setCost(payload.cost());
        return task;
    }
}
