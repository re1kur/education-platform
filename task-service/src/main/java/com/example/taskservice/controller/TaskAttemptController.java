package com.example.taskservice.controller;

import com.example.dto.TaskAttemptFullDto;
import com.example.taskservice.service.TaskAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/attempts/{id}")
@RequiredArgsConstructor
public class TaskAttemptController {
    private final TaskAttemptService service;

    @GetMapping
    public ResponseEntity<?> getAttempt(
            @PathVariable(name = "id") UUID attemptId,
            @AuthenticationPrincipal OidcUser user
            ) {
        TaskAttemptFullDto body = service.read(attemptId, user);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAttempt(
            @PathVariable(name = "id") UUID attemptId,
            @AuthenticationPrincipal OidcUser user
    ) {
        service.delete(attemptId, user);
        return ResponseEntity.noContent().build();
    }
}
