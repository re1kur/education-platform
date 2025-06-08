package re1kur.fileservice.service.impl;

import dto.PresignedUrl;
import exception.UrlUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.client.StoreClient;
import re1kur.fileservice.entity.File;
import re1kur.fileservice.mapper.FileMapper;
import re1kur.fileservice.repository.FileRepository;
import re1kur.fileservice.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFileService implements FileService {
    private final FileRepository repo;
    private final FileMapper mapper;
    private final StoreClient client;

    @Override
    public ResponseEntity<String> upload(MultipartFile payload) throws IOException {
        File file = mapper.upload(payload);
        String id = file.getId().toString();

        client.upload(id, payload);
        PresignedUrl resp = client.getUrl(id);
        file.setUrl(resp.url());
        file.setUrlExpiresAt(resp.expiration().atZone(ZoneId.systemDefault()));

        repo.save(file);
        return ResponseEntity.ok().body(id);
    }

    @Override
    public ResponseEntity<String> getUrl(String id) throws FileNotFoundException, UrlUpdateException {
        File file = repo.findById(UUID.fromString(id)).orElseThrow(() -> new FileNotFoundException("File with id '%s' does not exist."));
        if (ZonedDateTime.now().isAfter(file.getUrlExpiresAt().toInstant().atZone(ZoneId.systemDefault()))) {
            file = updateUrl(file);
        }
        return ResponseEntity.ok().body(file.getUrl());
    }

    private File updateUrl(File file) throws UrlUpdateException {
        PresignedUrl resp = client.getUrl(file.getId().toString());
        if (resp.expiration().equals(file.getUrlExpiresAt().toInstant().atZone(ZoneId.systemDefault()))) {
            throw new UrlUpdateException("The expiration has not been updated.");
        }
        file.setUrl(resp.url());
        file.setUrlExpiresAt(resp.expiration().atZone(ZoneId.systemDefault()));
        return repo.save(file);
    }
}
