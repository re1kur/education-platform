package re1kur.fileservice.service;

import exception.UrlUpdateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    ResponseEntity<String> upload(MultipartFile payload) throws IOException;

    ResponseEntity<String> getUrl(String id) throws FileNotFoundException, UrlUpdateException;
}
