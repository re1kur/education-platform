package re1kur.taskservice.controller;

import exception.TaskNotFoundException;
import exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.TaskDto;
import payload.TaskPayload;
import payload.TaskUpdatePayload;
import re1kur.taskservice.service.TaskService;

import java.util.List;


@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping("list")
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<TaskDto> list = service.getList();
        return ResponseEntity.ok().body(list);
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

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Integer id) throws TaskNotFoundException {
        return service.getById(id);
    }

}
