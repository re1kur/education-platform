package com.example.taskservice.controller;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.other.AttemptResultFilter;
import com.example.taskservice.service.TaskAttemptResultService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks/attempts/results")
@RequiredArgsConstructor
public class TasksAttemptsResultsController {
    private final TaskAttemptResultService service;

    @GetMapping
    public ResponseEntity<?> getAttemptsByFilter(
            @AuthenticationPrincipal Jwt jwt,
            @ModelAttribute @Nullable AttemptResultFilter filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PageDto<TaskAttemptResultDto> body = service.readAll(jwt, filter, page, size);
        return ResponseEntity.ok(body);
    }
}
