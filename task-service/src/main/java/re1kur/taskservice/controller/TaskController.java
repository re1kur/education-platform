package re1kur.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re1kur.taskservice.dto.TaskWriteDto;
import re1kur.taskservice.service.TaskService;


@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping("list")
    public ResponseEntity<String> getTasks() {
        String json = service.getList();
        return ResponseEntity.ok().body(json);
    }

    @PostMapping("create")
    public void create(@RequestBody TaskWriteDto task) {
        service.create(task);
    }

}
