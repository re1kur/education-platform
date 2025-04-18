package re1kur.fileservice.mapper;

import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.entity.File;

public interface FileMapper {
    File upload(MultipartFile payload);
}
