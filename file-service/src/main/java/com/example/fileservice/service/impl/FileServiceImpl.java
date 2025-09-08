package com.example.fileservice.service.impl;

import com.example.dto.PresignedUrl;
import com.example.exception.FileNotFoundException;
import com.example.exception.UrlUpdateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.fileservice.client.StoreClient;
import com.example.fileservice.entity.File;
import com.example.fileservice.mapper.FileMapper;
import com.example.fileservice.repository.FileRepository;
import com.example.fileservice.service.FileService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository repo;
    private final FileMapper mapper;
    private final StoreClient client;
    private final String FILE_NOT_FOUND_MESSAGE = "FILE [%s] WAS NOT FOUND.";

    @Override
    public List<UUID> upload(MultipartFile[] multipartFiles) {
        log.info("UPLOAD FILES REQUEST. LENGTH [{}]", multipartFiles.length);
        List<UUID> fileIds = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            UUID id = getFileId(multipartFile);
            fileIds.add(id);
        }
        log.info("SUCCESSFUL UPLOAD FILES REQUEST. [{}]", fileIds);
        return fileIds;
    }

    @Transactional
    private UUID getFileId(MultipartFile multipartFile) {
        UUID fileId = UUID.randomUUID();
        String fileIdString = fileId.toString();
        try {
            client.upload(fileIdString, multipartFile);
        } catch (IOException e) {
            log.error("COULDN'T UPLOAD FILE [{}] IN MINIO:\n [{}]", multipartFile.getOriginalFilename(), e.getMessage());
        }
        PresignedUrl resp = client.getUrl(fileIdString);

        File mapped = mapper.create(fileId, resp.url(), LocalDateTime.ofInstant(resp.expiration(), ZoneId.systemDefault()), multipartFile.getContentType());
        File saved = repo.save(mapped);

        return saved.getId();
    }

    @Override
    public String getUrl(UUID id) {
        log.info("GET FILE'S URL REQUEST: [{}]", id);

        File found = repo.findById(id)
                .orElseThrow(() -> new FileNotFoundException(FILE_NOT_FOUND_MESSAGE.formatted(id)));

        String url = checkConflicts(found);

        log.info("GOT FILE'S URL [{}]", url);
        return url;
    }

    private String checkConflicts(File found) {
        if (LocalDateTime.now().isAfter(found.getUrlExpiresAt())) {
            found = updateUrl(found);
        }
        return found.getUrl();
    }

    private File updateUrl(File file) {
        log.info("UPDATE FILE [{}] REQUEST", file.getId());

        PresignedUrl resp = client.getUrl(file.getId().toString());

        LocalDateTime expiresAt = LocalDateTime.ofInstant(resp.expiration(), ZoneId.systemDefault());
        if (expiresAt.equals(file.getUrlExpiresAt())) {
            throw new UrlUpdateException("The expiration has not been updated.");
        }

        File mapped = mapper.update(file, resp.url(), expiresAt);
        File saved = repo.save(mapped);

        log.info("UPDATED FILE [{}]", saved.getId());
        return saved;
    }
}
