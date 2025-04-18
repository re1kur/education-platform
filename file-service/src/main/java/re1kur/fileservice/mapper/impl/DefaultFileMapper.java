package re1kur.fileservice.mapper.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.entity.File;
import re1kur.fileservice.mapper.FileMapper;

import java.util.UUID;

@Component
public class DefaultFileMapper implements FileMapper {
    @Override
    public File upload(MultipartFile payload) {
        return File.builder()
                .id(UUID.randomUUID())
                .extension(payload.getContentType())
                .build();
    }
}
