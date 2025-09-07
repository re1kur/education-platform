package com.example.fileservice.mapper;

import com.example.fileservice.entity.File;

import java.time.LocalDateTime;
import java.util.UUID;

public interface FileMapper {
    File create(UUID fileId, String url, LocalDateTime localDateTime, String contentType);

    File update(File file, String url, LocalDateTime expiresAt);
}
