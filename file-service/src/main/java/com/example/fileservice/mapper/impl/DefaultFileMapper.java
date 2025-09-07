package com.example.fileservice.mapper.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.example.fileservice.entity.File;
import com.example.fileservice.mapper.FileMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DefaultFileMapper implements FileMapper {
    @Override
    public File create(MultipartFile payload) {

    }

    @Override
    public File create(UUID fileId, String url, LocalDateTime expiresAt, String contentType) {
        return File.builder()
                .id(fileId)
                .url(url)
                .urlExpiresAt(expiresAt)
                .extension(contentType)
                .build();
    }

    @Override
    public File update(File file, String url, LocalDateTime expiresAt) {
        file.setUrl(url);
        file.setUrlExpiresAt(expiresAt);

        return file;
    }
}
