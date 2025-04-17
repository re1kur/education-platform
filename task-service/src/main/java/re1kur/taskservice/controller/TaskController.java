package re1kur.taskservice.controller;

import dto.TaskPageDto;
import exception.TaskNotFoundException;
import exception.TrackNotFoundException;
import filter.TaskFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.TaskDto;
import payload.TaskPayload;
import payload.TaskUpdatePayload;
import re1kur.taskservice.service.TaskService;


@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping("list")
    public ResponseEntity<TaskPageDto> getTasks(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "6") Integer size,
            TaskFilter filter
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getPage(pageable, filter);
    }

    @PostMapping("create")
    public void create(@RequestBody TaskPayload payload) throws TrackNotFoundException {
        service.create(payload);
    }

    @PutMapping("update")
    public void update(@RequestBody TaskUpdatePayload payload) throws TrackNotFoundException, TaskNotFoundException {
        service.update(payload);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) throws TaskNotFoundException {
        service.delete(id);
    }

    @GetMapping("get")
    public ResponseEntity<TaskDto> getTask(@RequestParam Integer id) throws TaskNotFoundException {
        return service.getById(id);
    }

}
