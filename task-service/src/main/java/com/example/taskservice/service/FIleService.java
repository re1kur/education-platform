package com.example.taskservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FIleService {
    List<UUID> upload(MultipartFile[] files);
}
