package com.example.catalogueservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {
    UUID uploadFile(MultipartFile titleImage);

    List<UUID> uploadFiles(MultipartFile[] files);
}
