package re1kur.taskservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dto.TaskDto;
import payload.TaskPayload;
import re1kur.taskservice.entity.Task;
import re1kur.taskservice.mapper.TaskMapper;
import re1kur.taskservice.repository.TaskRepository;
import re1kur.taskservice.service.TaskService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {
    private final TaskRepository repo;
    private final TaskMapper mapper;

    @Override
    @SneakyThrows
    public List<TaskDto> getList() {
        return repo.findAll().stream()
                .map(mapper::read)
                .toList();
    }

    @Override
    @Transactional
    public void create(TaskPayload payload) {
        Task task = mapper.write(payload);
        repo.save(task);
    }
}
