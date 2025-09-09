package com.example.taskservice.service;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptFullDto;
import com.example.filter.AttemptsFilter;
import com.example.enums.TaskAttemptStatus;
import com.example.payload.TaskAttemptPayload;
import com.example.taskservice.entity.TaskAttempt;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface TaskAttemptService {
    TaskAttemptDto create(TaskAttemptPayload payload, UUID taskId, Jwt user, MultipartFile[] files);

    PageDto<TaskAttemptDto> readAll(Jwt user, UUID taskId, int page, int size);

    TaskAttemptFullDto read(UUID attemptId, Jwt user);

    void delete(UUID attemptId, Jwt user);

    PageDto<TaskAttemptDto> readAll(Jwt user, AttemptsFilter filter, int page, int size);

    TaskAttempt get(UUID attemptId, Jwt user);

    void setStatus(TaskAttempt found, TaskAttemptStatus status);
}
