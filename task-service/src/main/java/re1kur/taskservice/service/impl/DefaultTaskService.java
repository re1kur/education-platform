package re1kur.taskservice.service.impl;

import dto.TaskPageDto;
import exception.InternalServerErrorException;
import exception.TaskNotFoundException;
import exception.TrackNotFoundException;
import filter.TaskFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dto.TaskDto;
import payload.TaskPayload;
import payload.TaskUpdatePayload;
import re1kur.taskservice.entity.Task;
import re1kur.taskservice.mapper.TaskMapper;
import re1kur.taskservice.repository.TaskRepository;
import re1kur.taskservice.repository.UserTaskRepository;
import re1kur.taskservice.service.TaskService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {
    private final TaskRepository repo;
    private final UserTaskRepository userTaskRepo;
    private final TaskMapper mapper;

    @Override
    public List<TaskDto> getList() {
        return repo.findAll().stream()
                .map(mapper::read)
                .toList();
    }

    @Override
    @Transactional
    public void create(TaskPayload payload) throws TrackNotFoundException {
        Task task = mapper.write(payload);
        repo.save(task);
    }

    @Override
    public void update(TaskUpdatePayload payload) throws TrackNotFoundException, TaskNotFoundException {
        Task task = repo.findById(payload.id())
                .orElseThrow(() -> new TaskNotFoundException(
                        "Task with id %d does not exist.".formatted(payload.id())));
        Task update = mapper.update(task, payload);
        repo.save(update);
    }

    @Override
    public void delete(Integer id) throws TaskNotFoundException {
        Task task = repo.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task with id %d does not exist.".formatted(id)));
        repo.delete(task);
    }

    @Override
    public TaskDto getById(Integer id) throws TaskNotFoundException {
        return repo.findById(id).map(mapper::read).orElseThrow(
                () -> new TaskNotFoundException("Task with id %d does not exist.".formatted(id)));
    }

    @Override
    public TaskPageDto getPage(Pageable pageable, TaskFilter filter) {
        String name = filter.name();
        BigDecimal cost = filter.cost();
        Integer level = filter.level();
        return TaskPageDto.of(repo.findAllByFilter(pageable, name, cost, level).map(mapper::read));
    }

    @Override
    public void attachFile(String userId, Integer taskId, String fileId) throws TaskNotFoundException {
        repo.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task with id %d does not exist.".formatted(taskId)));
        try {
            userTaskRepo.addUsersFile(taskId, UUID.fromString(userId), UUID.fromString(fileId));
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
