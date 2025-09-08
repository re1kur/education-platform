package com.example.taskservice.service.impl;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.exception.TaskAttemptResultNotFoundException;
import com.example.other.AttemptResultFilter;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.entity.TaskAttemptResult;
import com.example.taskservice.mapper.TaskAttemptResultMapper;
import com.example.taskservice.repository.TaskAttemptResultRepository;
import com.example.taskservice.service.TaskAttemptResultService;
import com.example.taskservice.service.TaskAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public TaskAttemptResultDto create(TaskAttemptResultPayload payload, UUID attemptId, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("CREATE RESULT FOR TASK ATTEMPT [{}] REQUEST BY ADMIN [{}]", attemptId, userId);

        TaskAttempt found = attemptService.get(attemptId, jwt);
        TaskAttemptResult mapped = mapper.create(payload, userId, found);

        TaskAttemptResult saved = repo.save(mapped);

        log.info("CREATED TASK ATTEMPT [{}] RESULT BY ADMIN [{}]", saved.getTaskAttempt().getId(), saved.getCheckedBy());
        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID attemptId, Jwt user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("DELETE RESULT FOR TASK ATTEMPT [{}] REQUEST BY ADMIN [{}]", attemptId, userId);

        TaskAttemptResult taskAttemptResult = repo.findById(attemptId)
                .orElseThrow(() -> new TaskAttemptResultNotFoundException(resultNotFoundMessage.formatted(attemptId)));

        repo.delete(taskAttemptResult);

        log.info("DELETED TASK ATTEMPT [{}] RESULT BY ADMIN [{}]", attemptId, userId);
    }

    @Override
    public TaskAttemptResultDto read(UUID attemptId, Jwt user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("READ RESULT FOR TASK ATTEMPT [{}] REQUEST BY ADMIN [{}]", attemptId, userId);

        TaskAttemptResult taskAttemptResult = repo.findById(attemptId)
                .orElseThrow(() -> new TaskAttemptResultNotFoundException(resultNotFoundMessage.formatted(attemptId)));

        return mapper.read(taskAttemptResult);
    }

    @Override
    public PageDto<TaskAttemptResultDto> readAll(Jwt jwt, AttemptResultFilter filter, int page, int size) {
        UUID adminId = UUID.fromString(jwt.getSubject());
        log.info("READ ALL ATTEMPT RESULTS BY FILTER [{}] BY ADMIN [{}]", filter.toString(), adminId);
        Pageable pageable = PageRequest.of(page, size);


        UUID checkedBy = filter.checkedBy();
        UUID attemptId = filter.attemptId();
        String comment = filter.comment();

        Page<TaskAttemptResult> results = repo.findAll(pageable, checkedBy, attemptId, comment);

        return mapper.readPage(results);
    }
}
