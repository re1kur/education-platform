package com.example.taskservice.service.impl;

import com.example.exception.FileCountLimitExceededException;
import com.example.exception.FileReadException;
import com.example.taskservice.service.FIleService;
import com.example.taskservice.util.MultipartInputStreamFileResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FIleServiceImpl implements FIleService {
    private final RestTemplate restTemplate;

    @Value("${custom.file-service-url}")
    private final String FILE_SERVICE_URL;
    private static final String FILE_READ_MESSAGE = "FILE [%s] COULDN'T READ:\n %S";

    @Override
    public List<UUID> upload(MultipartFile[] files) {
        if (files == null) return null;
        int length = files.length;
        log.info("UPLOAD FILES FUNCTION. LENGTH OF FILES ARRAY: [{}]", length);
        if (length >= 3) throw new FileCountLimitExceededException("File's count must be lesser than 3.");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            try {
                body.add("files", new MultipartInputStreamFileResource(
                        file.getInputStream(), originalFilename));
            } catch (IOException e) {
                throw new FileReadException(FILE_READ_MESSAGE.formatted(originalFilename, e.getMessage()));
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        log.info("FILE UPLOAD REQUEST. FILES [{}]", Arrays.stream(files)
                .map(MultipartFile::getOriginalFilename).toList());

        Map response = restTemplate.postForObject(FILE_SERVICE_URL, requestEntity, Map.class);
        List<UUID> fileIds = (List<UUID>) response.get("fileIds");

        log.info("FILE UPLOAD REQUEST IS SUCCESS: [{}]", fileIds);

        return fileIds;
    }
}
