package com.example.taskservice.controller;

import com.example.dto.TaskAttemptResultDto;
import com.example.payload.TaskAttemptResultPayload;
import com.example.taskservice.service.TaskAttemptResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/attempts/{attemptId}/results}")
@RequiredArgsConstructor
public class TaskAttemptResultController {
    private final TaskAttemptResultService service;

    @PostMapping
    public ResponseEntity<?> createResult(
            @AuthenticationPrincipal OidcUser user,
            @RequestBody TaskAttemptResultPayload payload,
            @PathVariable(name = "attemptId") UUID attemptId
            ) {
        TaskAttemptResultDto body = service.create(payload, attemptId, user);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<?> readResult(
            @AuthenticationPrincipal OidcUser user,
            @PathVariable(name = "attemptId") UUID attemptId
    ) {
        TaskAttemptResultDto body = service.read(attemptId, user);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteResult(
            @AuthenticationPrincipal OidcUser user,
            @PathVariable(name = "attemptId") UUID attemptId
    ) {
        service.delete(attemptId, user);
        return ResponseEntity.noContent().build();
    }
}
