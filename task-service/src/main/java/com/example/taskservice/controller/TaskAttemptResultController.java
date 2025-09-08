package com.example.taskservice.controller;

import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.service.TaskAttemptResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/attempts/{attemptId}/results")
@RequiredArgsConstructor
public class TaskAttemptResultController {
    private final TaskAttemptResultService service;

    @PostMapping
    public ResponseEntity<?> createResult(
            @RequestBody TaskAttemptResultPayload payload,
            @PathVariable(name = "attemptId") UUID attemptId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        TaskAttemptResultDto body = service.create(payload, attemptId, jwt);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<?> readResult(
            @PathVariable(name = "attemptId") UUID attemptId,
            @AuthenticationPrincipal Jwt jwt
            ) {
        TaskAttemptResultDto body = service.read(attemptId, jwt);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteResult(
            @PathVariable(name = "attemptId") UUID attemptId,
            @AuthenticationPrincipal Jwt jwt
            ) {
        service.delete(attemptId, jwt);
        return ResponseEntity.noContent().build();
    }
}
