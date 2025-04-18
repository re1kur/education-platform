package re1kur.fileservice.service.impl;

import dto.PresignedUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.client.StoreClient;
import re1kur.fileservice.entity.File;
import re1kur.fileservice.mapper.FileMapper;
import re1kur.fileservice.repository.FileRepository;
import re1kur.fileservice.service.FileService;

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

        client.upload(file.getId(), payload);
        PresignedUrl resp = client.getUrl(file.getId());
        file.setUrl(resp.url());
        file.setUrlExpiresAt(resp.expiration().atZone(ZoneId.systemDefault()));

        File save = repo.save(file);
        return ResponseEntity.ok().body(save.getId().toString());
    }

    @Override
    public ResponseEntity<String> getUrl(String id) {
        return ResponseEntity.ok().body("");
        // TODO
    }
}
