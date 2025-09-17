package com.example.catalogueservice.controller;

import com.example.catalogueservice.outbox.OutboxEventService;
import com.example.dto.OutboxEventDto;
import com.example.enums.OutboxType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outbox")
public class OutboxController {
    private final OutboxEventService service;

    @GetMapping
    public ResponseEntity<?> getEvents(
            @RequestParam(value = "type") OutboxType type
            ) {
        List<OutboxEventDto> body = service.readAll(type);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<?> reserveEvent(
            @PathVariable(name = "id") UUID id
            ) {
        service.reserve(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<?> releaseEvent(
            @PathVariable(name = "id") UUID id
            ) {
        service.release(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable(name = "id") UUID id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
