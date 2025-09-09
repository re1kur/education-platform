package com.example.catalogueservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    UUID uploadFile(MultipartFile titleImage);
}
