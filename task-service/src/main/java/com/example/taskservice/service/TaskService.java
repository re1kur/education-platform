package com.example.taskservice.service;

import com.example.dto.TaskDto;
import com.example.dto.TaskPageDto;
import com.example.other.TaskFilter;
import com.example.payload.TaskPayload;
import com.example.payload.TaskUpdatePayload;
import com.example.taskservice.entity.Task;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface TaskService {
    List<TaskDto> getList();

    TaskDto create(TaskPayload payload, MultipartFile[] files, Jwt user);

    TaskDto update(UUID id, TaskUpdatePayload payload);

    void delete(UUID id, Jwt jwt);

    TaskDto read(UUID id);

    TaskPageDto getPage(TaskFilter filter, int page, int size);

    Task get(UUID id);
}
