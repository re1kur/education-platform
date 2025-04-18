package re1kur.fileservice.client.impl;

import dto.PresignedUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.client.StoreClient;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultStoreClient implements StoreClient {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${minio.default-bucket}")
    private String bucket;

    @Value("${minio.url.time-to-live}")
    private Integer ttl;

    @Override
    public void upload(UUID id, MultipartFile payload) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .key(id.toString())
                .bucket(bucket)
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(payload.getInputStream(), payload.getSize()));
    }

    @Override
    public PresignedUrl getUrl(UUID id) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(id.toString())
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(ttl))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);
        return new PresignedUrl(presignedGetObjectRequest.url().toExternalForm(), presignedGetObjectRequest.expiration());
    }
}
