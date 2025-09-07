package com.example.fileservice.client;

import com.example.dto.PresignedUrl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StoreClient {
    void upload(String id, MultipartFile payload) throws IOException;

    PresignedUrl getUrl(String id);
}
