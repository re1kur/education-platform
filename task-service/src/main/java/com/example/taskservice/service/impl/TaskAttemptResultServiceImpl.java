package com.example.taskservice.service.impl;

import com.example.dto.TaskAttemptResultDto;
import com.example.exception.TaskAttemptNotFoundException;
import com.example.exception.TaskAttemptResultNotFoundException;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.entity.TaskAttemptResult;
import com.example.taskservice.mapper.TaskAttemptResultMapper;
import com.example.taskservice.repository.TaskAttemptResultRepository;
import com.example.taskservice.service.TaskAttemptResultService;
import com.example.taskservice.service.TaskAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskAttemptResultServiceImpl implements TaskAttemptResultService {
    private final TaskAttemptResultRepository repo;
    private final TaskAttemptResultMapper mapper;

    private final TaskAttemptService attemptService;
    private final String resultNotFoundMessage = "TASK ATTEMPT [%S] RESULT WAS NOT FOUND";

    @Override
    @Transactional
    public TaskAttemptResultDto create(TaskAttemptResultPayload payload, UUID attemptId, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("CREATE RESULT FOR TASK ATTEMPT [{}] REQUEST BY ADMIN [{}]", attemptId, userId);

        TaskAttempt found = attemptService.get(attemptId, user);
        TaskAttemptResult mapped = mapper.create(payload, userId, found);

        TaskAttemptResult saved = repo.save(mapped);

        log.info("CREATED TASK ATTEMPT [{}] RESULT BY ADMIN [{}]", saved.getTaskAttempt().getId(), saved.getCheckedBy());
        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID attemptId, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("DELETE RESULT FOR TASK ATTEMPT [{}] REQUEST BY ADMIN [{}]", attemptId, userId);

        TaskAttemptResult taskAttemptResult = repo.findById(attemptId)
                .orElseThrow(() -> new TaskAttemptResultNotFoundException(resultNotFoundMessage.formatted(attemptId)));

        repo.delete(taskAttemptResult);

        log.info("DELETED TASK ATTEMPT [{}] RESULT BY ADMIN [{}]", attemptId, userId);
    }

    @Override
    public TaskAttemptResultDto read(UUID attemptId, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("READ RESULT FOR TASK ATTEMPT [{}] REQUEST BY ADMIN [{}]", attemptId, userId);

        TaskAttemptResult taskAttemptResult = repo.findById(attemptId)
                .orElseThrow(() -> new TaskAttemptResultNotFoundException(resultNotFoundMessage.formatted(attemptId)));

        return mapper.read(taskAttemptResult);
    }
}
