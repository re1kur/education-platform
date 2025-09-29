package com.example.taskservice.controller;

import com.example.dto.OutboxEventDto;
import com.example.taskservice.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outbox")
public class OutboxController {
    private final OutboxService service;

    @GetMapping
    public ResponseEntity<?> readEvents() {
        List<OutboxEventDto> body = service.readAll();

        return ResponseEntity.ok(body);
    }

}
