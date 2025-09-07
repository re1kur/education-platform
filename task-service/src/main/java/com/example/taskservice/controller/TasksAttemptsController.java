package com.example.taskservice.controller;

import com.example.dto.PageDto;
import com.example.dto.TaskAttemptDto;
import com.example.other.AttemptFilter;
import com.example.taskservice.service.TaskAttemptService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks/attempts")
@RequiredArgsConstructor
public class TasksAttemptsController {
    private final TaskAttemptService service;

    @GetMapping
    public ResponseEntity<?> getAttemptsByFilter(
            @AuthenticationPrincipal OidcUser user,
            @ModelAttribute @Nullable AttemptFilter filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PageDto<TaskAttemptDto> body = service.readAll(user, filter, page, size);
        return ResponseEntity.ok(body);
    }

}
