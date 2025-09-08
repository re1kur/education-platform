package com.example.taskservice.controller;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.payload.TaskAttemptPayload;
import com.example.taskservice.service.TaskAttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/{taskId}/attempts")
@RequiredArgsConstructor
public class TaskAttemptsController {
    private final TaskAttemptService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAttempt(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "taskId") UUID taskId,
            @RequestPart(name = "payload") @Valid TaskAttemptPayload payload,
            @RequestPart(name = "files", required = false) MultipartFile[] files
            ) {
        TaskAttemptDto body = service.create(payload, taskId, jwt, files);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<?> getAttempts(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "taskId") UUID taskId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PageDto<TaskAttemptDto> body =  service.readAll(jwt, taskId, page, size);
        return ResponseEntity.ok(body);
    }
}
