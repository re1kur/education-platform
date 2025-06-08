package re1kur.fileservice.client;

import dto.PresignedUrl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StoreClient {
    void upload(String id, MultipartFile payload) throws IOException;

    PresignedUrl getUrl(String id);
}
