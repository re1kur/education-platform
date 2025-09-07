package com.example.taskservice.service;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptFullDto;
import com.example.other.AttemptFilter;
import com.example.payload.TaskAttemptPayload;
import com.example.taskservice.entity.TaskAttempt;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface TaskAttemptService {
    TaskAttemptDto create(TaskAttemptPayload payload, UUID taskId, OidcUser user, MultipartFile[] files);

    PageDto<TaskAttemptDto> readAll(OidcUser user, UUID taskId, int page, int size);

    TaskAttemptFullDto read(UUID attemptId, OidcUser user);

    void delete(UUID attemptId, OidcUser user);

    PageDto<TaskAttemptDto> readAll(OidcUser user, AttemptFilter filter, int page, int size);

    TaskAttempt get(UUID attemptId, OidcUser user);
}
