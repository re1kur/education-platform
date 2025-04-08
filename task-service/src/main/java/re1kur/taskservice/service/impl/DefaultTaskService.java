package re1kur.taskservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re1kur.taskservice.dto.TaskDto;
import re1kur.taskservice.dto.TaskWriteDto;
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
    private final ObjectMapper serializer;

    @Override
    @SneakyThrows
    public String getList() {
        List<TaskDto> list = repo.findAll().stream()
                .map(mapper::read)
                .toList();
        return serializer.writeValueAsString(list);
    }

    @Override
    @Transactional
    public void create(TaskWriteDto dto) {
        Task task = mapper.write(dto);
        repo.save(task);
    }
}
