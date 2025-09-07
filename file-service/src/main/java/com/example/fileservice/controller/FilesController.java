package com.example.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.fileservice.service.FileService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FilesController {
    private final FileService service;

    @PostMapping
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") MultipartFile[] files
    ) {
        List<UUID> body = service.upload(files);
        return ResponseEntity.ok(Map.of("fileIds", body));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getFileUrl(
            @PathVariable("id") UUID id
    ) {
        String body = service.getUrl(id);
        return ResponseEntity.ok(body);
    }
}
