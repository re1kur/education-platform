package com.example.catalogueservice.service.impl;

import com.example.catalogueservice.service.FileService;
import com.example.catalogueservice.util.MultipartInputStreamFileResource;
import com.example.exception.FileCountLimitExceededException;
import com.example.exception.FileReadException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final RestTemplate restTemplate;

    @Value("${custom.file-service-url}")
    private String FILE_SERVICE_URL;

    private String FILE_READ_MESSAGE = "FILE [%s] COULDN'T READ:\n %s";

    @Override
    public UUID uploadFile(MultipartFile file) {
        if (file == null) return null;

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        String originalFilename = file.getOriginalFilename();
        try {
            body.add("files", new MultipartInputStreamFileResource(
                    file.getInputStream(), originalFilename));
        } catch (IOException e) {
            throw new FileReadException(FILE_READ_MESSAGE.formatted(originalFilename, e.getMessage()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        log.info("FILE UPLOAD REQUEST. FILE [{}]", file.getOriginalFilename());

        Map response = restTemplate.postForObject(FILE_SERVICE_URL, requestEntity, Map.class);

        List<String> fileIdsStr = (List<String>) response.get("fileIds");
        List<UUID> fileIds = fileIdsStr.stream().map(UUID::fromString).toList();


        log.info("FILE UPLOAD REQUEST IS SUCCESS: [{}]", fileIds);

        return fileIds.getFirst();
    }

    @Override
    public List<UUID> uploadFiles(MultipartFile[] files) {
        if (files == null) return null;

        int length = files.length;
        log.info("UPLOAD FILES FUNCTION. LENGTH OF FILES ARRAY: [{}]", length);
        if (length >= 3) throw new FileCountLimitExceededException("FILE COUNT HAVE TO BE LESSER THAN 3.");

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

        List<String> fileIdsStr = (List<String>) response.get("fileIds");
        List<UUID> fileIds = fileIdsStr.stream().map(UUID::fromString).toList();


        log.info("FILE UPLOAD REQUEST IS SUCCESS: [{}]", fileIds);

        return fileIds;
    }
}
