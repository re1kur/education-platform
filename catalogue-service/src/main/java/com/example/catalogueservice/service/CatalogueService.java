package com.example.catalogueservice.service;

import com.example.dto.CatalogueProductDto;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.payload.CatalogueProductPayload;
import com.example.payload.UpdateCataloguePayloadPayload;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;


public interface CatalogueService {
    void delete(UUID productId, Jwt jwt);

    CatalogueProductDto update(UUID id, UpdateCataloguePayloadPayload payload, Jwt jwt);

    PageDto<ProductDto> readAll(int page, int size);

    CatalogueProductDto create(CatalogueProductPayload payload, Jwt jwt);
}
