package com.example.fileservice.client.impl;

import com.example.dto.PresignedUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.example.fileservice.client.StoreClient;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class StoreClientImpl implements StoreClient {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${minio.default-bucket}")
    private String bucket;

    @Value("${minio.url.time-to-live}")
    private Integer ttl;

    @Override
    public void upload(String id, MultipartFile payload) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .key(id)
                .bucket(bucket)
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(payload.getInputStream(), payload.getSize()));
    }

    @Override
    public PresignedUrl getUrl(String id) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(id)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(ttl))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);
        return new PresignedUrl(presignedGetObjectRequest.url().toExternalForm(), presignedGetObjectRequest.expiration());
    }
}
