package com.example.taskservice.service;

import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.UUID;

public interface TaskAttemptResultService {
    TaskAttemptResultDto create(TaskAttemptResultPayload payload, UUID attemptId, OidcUser user);

    void delete(UUID attemptId, OidcUser user);

    TaskAttemptResultDto read(UUID attemptId, OidcUser user);
}
