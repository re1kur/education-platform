package com.example.taskservice.service.impl;

import com.example.dto.TaskDto;
import com.example.dto.TaskPageDto;
import com.example.exception.TaskNotFoundException;
import com.example.other.TaskFilter;
import com.example.payload.TaskPayload;
import com.example.payload.TaskUpdatePayload;
import com.example.taskservice.service.FIleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    private final FIleService fIleService;


    private final String messageNotFound = "TASK [%s] WAS NOT FOUND.";

    @Override
    public List<TaskDto> getList() {
        return repo.findAll().stream()
                .map(mapper::read)
                .toList();
    }

    @Override
    @Transactional
    public TaskDto create(TaskPayload payload, MultipartFile[] files, OidcUser user) {
        UUID userId = UUID.fromString(user.getSubject());
        log.info("CREATE TASK REQUEST BY USER [{}]", userId);

        List<UUID> fileIds = fIleService.upload(files);
        Task mapped = mapper.write(payload, fileIds);
        Task saved = repo.save(mapped);

        log.info("CREATED TASK [{}] BY USER [{}]", saved.getId(), userId);

        return mapper.read(saved);
    }

    @Override
    @Transactional
    public TaskDto update(UUID id, TaskUpdatePayload payload) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(id)));
        Task update = mapper.update(task, payload);

        Task saved = repo.save(update);

        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(id)));
        repo.delete(task);
    }

    @Override
    public TaskDto read(UUID id) {
        return repo.findById(id).map(mapper::read)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(id)));
    }

    @Override
    public TaskPageDto getPage(TaskFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        String name = filter.name();
        BigDecimal cost = filter.cost();

        return TaskPageDto.of(repo.findAllByFilter(pageable, name, cost).map(mapper::read));
    }

    @Override
    public Task get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(messageNotFound.formatted(id)));
    }
}
