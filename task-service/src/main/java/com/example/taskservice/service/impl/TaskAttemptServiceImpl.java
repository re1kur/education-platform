package com.example.taskservice.service.impl;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptFullDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.exception.AttemptNotFoundException;
import com.example.exception.TaskAttemptNotFoundException;
import com.example.other.AttemptFilter;
import com.example.other.TaskAttemptStatus;
import com.example.payload.TaskAttemptPayload;
import com.example.taskservice.entity.Task;
import com.example.taskservice.entity.TaskAttempt;
import com.example.taskservice.mapper.TaskAttemptMapper;
import com.example.taskservice.mapper.TaskAttemptResultMapper;
import com.example.taskservice.repository.TaskAttemptRepository;
import com.example.taskservice.service.FIleService;
import com.example.taskservice.service.TaskAttemptService;
import com.example.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskAttemptServiceImpl implements TaskAttemptService {
    private final TaskAttemptRepository repo;
    private final TaskAttemptMapper mapper;
    private final TaskAttemptResultMapper resultMapper;
    private final TaskService taskService;
    private final String attemptNotFoundMessage = "Task attempt [%s] was not found.";
    private final FIleService fileService;

    @Override
    @Transactional
    public TaskAttemptDto create(TaskAttemptPayload payload, UUID taskId, OidcUser user, MultipartFile[] files) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("CREATE ATTEMPT FOR TASK [{}] BY USER [{}]", taskId, userId);

        Task task = taskService.get(taskId);
        List<UUID> fileIds = fileService.upload(files);
        TaskAttempt mapped = mapper.create(payload, task, userId, fileIds);

        TaskAttempt saved = repo.save(mapped);
        UUID attemptId = saved.getId();

        log.info("TASK ATTEMPT [{}] CREATED BY USER [{}]", attemptId, userId);
        return mapper.read(saved);
    }

    @Override
    public PageDto<TaskAttemptDto> readAll(OidcUser user, UUID taskId, int page, int size) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("READ ALL ATTEMPTS BY TASK [{}] BY USER [{}]", taskId, userId);
        Pageable pageable = PageRequest.of(page, size);

        Page<TaskAttempt> attempts = repo.findAllByTaskIdAndUserId(pageable, taskId, userId);

        return mapper.readPage(attempts);
    }

    @Override
    public TaskAttemptFullDto read(UUID attemptId, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("READ ATTEMPT [{}] BY USER [{}]", attemptId, userId);

        TaskAttempt found = repo.findById(attemptId)
                .orElseThrow(() -> new AttemptNotFoundException(attemptNotFoundMessage.formatted(attemptId)));
        TaskAttemptResultDto resultDto = resultMapper.read(found.getResult());

        return mapper.readFull(found, resultDto);
    }

    @Override
    @Transactional
    public void delete(UUID attemptId, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("DELETE ATTEMPT [{}] BY USER [{}]", attemptId, userId);

        TaskAttempt found = repo.findByIdAndUserId(attemptId, userId)
                .orElseThrow(() -> new AttemptNotFoundException(attemptNotFoundMessage.formatted(attemptId)));
        repo.delete(found);

        log.info("TASK ATTEMPT [{}] DELETED BY USER [{}]", attemptId, userId);
    }

    @Override
    public PageDto<TaskAttemptDto> readAll(OidcUser user, AttemptFilter filter, int page, int size) {
        UUID adminId = UUID.fromString(user.getSubject());
        log.info("READ ALL ATTEMPTS BY FILTER [{}] BY ADMIN [{}]", filter.toString(), adminId);
        Pageable pageable = PageRequest.of(page, size);


        UUID userId = filter.userId();
        UUID taskId = filter.taskId();
        TaskAttemptStatus status = filter.status();

        Page<TaskAttempt> attempts = repo.findAll(pageable, userId, taskId, status);

        return mapper.readPage(attempts);
    }

    @Override
    public TaskAttempt get(UUID attemptId, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("READ ATTEMPT [{}] BY USER [{}]", attemptId, userId);

        return repo.findById(attemptId)
                .orElseThrow(() -> new TaskAttemptNotFoundException(attemptNotFoundMessage.formatted(attemptId)));
    }
}
