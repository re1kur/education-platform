package re1kur.taskservice.controller;

import dto.TaskPageDto;
import exception.TaskNotFoundException;
import exception.TrackNotFoundException;
import filter.TaskFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
        TaskPageDto body = service.getPage(pageable, filter);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping("create")
    public void create(@RequestBody @Valid TaskPayload payload) throws TrackNotFoundException {
        service.create(payload);
    }

    @PutMapping("update")
    public void update(@RequestBody @Valid TaskUpdatePayload payload) throws TrackNotFoundException, TaskNotFoundException {
        service.update(payload);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam Integer id) throws TaskNotFoundException {
        service.delete(id);
    }

    @GetMapping("get")
    public ResponseEntity<TaskDto> getTask(@RequestParam Integer id) throws TaskNotFoundException {
        TaskDto body = service.getById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(body);
    }

    @PostMapping("attach-file")
    public ResponseEntity<String> attachFile(
            @RequestParam String fileId,
            @RequestParam Integer taskId,
            @AuthenticationPrincipal UserDetails principal
    ) throws TaskNotFoundException {
        String userId = principal.getUsername();
        service.attachFile(userId, taskId, fileId);
        return ResponseEntity.ok().body("File \"%s\" was attached to task №%d by user with id \"%s\".".formatted(fileId, taskId, userId));
    }

}
