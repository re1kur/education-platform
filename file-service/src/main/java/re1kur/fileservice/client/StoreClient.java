package re1kur.fileservice.client;

import dto.PresignedUrl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface StoreClient {
    void upload(UUID id, MultipartFile payload) throws IOException;

    PresignedUrl getUrl(UUID id);
}
