package com.example.taskservice.controller;

import com.example.dto.TaskDto;
import com.example.payload.TaskUpdatePayload;
import com.example.taskservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/{id}")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @DeleteMapping
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        service.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getTask(
            @PathVariable UUID id
    ) {
        TaskDto body = service.read(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid TaskUpdatePayload payload
    ) {
        TaskDto body = service.update(id, payload);
        return ResponseEntity.ok(body);
    }
}
