package com.example.taskservice.controller;

import com.example.dto.TaskDto;
import com.example.dto.TaskPageDto;
import com.example.other.TaskFilter;
import com.example.payload.TaskPayload;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.example.taskservice.service.TaskService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TaskService service;

    @GetMapping
    public ResponseEntity<?> getTasks(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "6") Integer size,
            @ModelAttribute @Nullable TaskFilter filter
    ) {
        TaskPageDto body = service.getPage(filter, page, size);
        return ResponseEntity.ok(body);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestPart(name = "payload") @Valid TaskPayload payload,
            @RequestPart(name = "files", required = false) MultipartFile[] files,
            @AuthenticationPrincipal Jwt jwt
            ) {
        TaskDto body = service.create(payload, files, jwt);
        return ResponseEntity.ok(body);
    }
}
