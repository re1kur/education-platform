package com.example.taskservice.service;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.other.AttemptResultFilter;
import com.example.payload.TaskAttemptResultPayload;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface TaskAttemptResultService {
    TaskAttemptResultDto create(TaskAttemptResultPayload payload, UUID attemptId, Jwt user);

    void delete(UUID attemptId, Jwt user);

    TaskAttemptResultDto read(UUID attemptId, Jwt user);

    PageDto<TaskAttemptResultDto> readAll(Jwt jwt, AttemptResultFilter filter, int page, int size);
}
