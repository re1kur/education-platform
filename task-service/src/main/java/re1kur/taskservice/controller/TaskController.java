package re1kur.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.TaskDto;
import payload.TaskPayload;
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
    public void create(@RequestBody TaskPayload payload) {
        service.create(payload);
    }

}
