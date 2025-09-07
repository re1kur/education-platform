package com.example.fileservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {
    List<UUID> upload(MultipartFile[] payload);

    String getUrl(UUID id);
}
