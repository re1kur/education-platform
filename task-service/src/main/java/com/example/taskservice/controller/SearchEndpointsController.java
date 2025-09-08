package com.example.taskservice.controller;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.dto.TaskAttemptResultDto;
import com.example.other.AttemptFilter;
import com.example.other.AttemptResultFilter;
import com.example.taskservice.service.TaskAttemptResultService;
import com.example.taskservice.service.TaskAttemptService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SearchEndpointsController {
    private final TaskAttemptService taskAttemptService;
    private final TaskAttemptResultService taskAttemptResultService;

    @GetMapping("/api/v1/tasks/attempts")
    public ResponseEntity<?> getAttemptsByFilter(
            @AuthenticationPrincipal Jwt jwt,
            @ModelAttribute @Nullable AttemptFilter filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PageDto<TaskAttemptDto> body = taskAttemptService.readAll(jwt, filter, page, size);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/api/v1/tasks/attempts/results")
    public ResponseEntity<?> getAttemptsByFilter(
            @AuthenticationPrincipal Jwt jwt,
            @ModelAttribute @Nullable AttemptResultFilter filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PageDto<TaskAttemptResultDto> body = taskAttemptResultService.readAll(jwt, filter, page, size);
        return ResponseEntity.ok(body);
    }
}
