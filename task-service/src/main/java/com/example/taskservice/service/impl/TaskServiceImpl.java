package com.example.taskservice.service.impl;

import com.example.dto.PageDto;
import com.example.dto.TaskDto;
import com.example.exception.TaskConflictException;
import com.example.exception.TaskNotFoundException;
import com.example.filter.TasksFilter;
import com.example.payload.TaskPayload;
import com.example.payload.TaskUpdatePayload;
import com.example.taskservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskservice.entity.Task;
import com.example.taskservice.mapper.TaskMapper;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.service.TaskService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repo;
    private final TaskMapper mapper;
    private final FileService fIleService;
    private final JdbcTemplate jdbcTemplate;


    private String messageNotFound = "TASK [%s] WAS NOT FOUND.";

    @Override
    public List<TaskDto> getList() {
        return repo.findAll().stream()
                .map(mapper::read)
                .toList();
    }

    @Override
    @Transactional
    public TaskDto create(TaskPayload payload, MultipartFile[] files, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("CREATE TASK REQUEST BY USER [{}]", userId);

        List<UUID> fileIds = fIleService.upload(files);
        Task mapped = mapper.write(payload, fileIds);
        Task saved = repo.save(mapped);

        log.info("CREATED TASK [{}] BY USER [{}]", saved.getId(), userId);

        return mapper.read(saved);
    }

    @Override
    @Transactional
    public TaskDto update(UUID taskId, TaskUpdatePayload payload, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("UPDATE TASK [{}] REQUEST BY USER [{}]", taskId, userId);

        Task task = repo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(taskId)));
        Task update = mapper.update(task, payload);

        Task saved = repo.save(update);

        log.info("UPDATED TASK [{}] BY USER [{}]", taskId, userId);
        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID taskId, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("DELETE TASK [{}] REQUEST BY USER [{}]", taskId, userId);

        Task task = repo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(taskId)));

        repo.delete(task);

        log.info("DELETED TASK [{}] BY USER [{}]", taskId, userId);
    }

    @Override
    public TaskDto read(UUID id) {
        return repo.findById(id).map(mapper::read)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(id)));
    }

    @Override
    public PageDto<TaskDto> getPage(TasksFilter filter, int page, int size, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("READ ALL TASKS REQUEST BY USER [{}]", userId);

        Pageable pageable = PageRequest.of(page, size);

        String name = filter.name();
        BigDecimal cost = filter.cost();

        Page<Task> foundPage = repo.findAll(pageable, name, cost);

        return mapper.readPage(foundPage);
    }

    @Override
    public Task get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(id)));
    }

    @Override
    @Transactional
    public void setCompleted(Task task, UUID userId) {
        UUID taskId = task.getId();
        log.info("USER [{}] HAS COMPLETED TASK [{}]", userId, taskId);

        int updated = jdbcTemplate.update(
                "INSERT INTO user_completed_tasks (task_id, user_id) VALUES (?, ?) ON CONFLICT DO NOTHING",
                taskId, userId);

        if (updated == 0) throw new TaskConflictException("TASK [%s] ALREADY COMPLETED BY USER [%s]".formatted(taskId, userId));

        // todo: reward logic here
    }
}