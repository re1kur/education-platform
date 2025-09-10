package com.example.catalogueservice.service;

import com.example.catalogueservice.entity.Product;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.filter.ProductsFilter;
import com.example.payload.ProductPayload;
import com.example.payload.ProductUpdatePayload;
import com.example.catalogueservice.exception.ProductNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProductService {
    ProductDto read(UUID id);

    ProductDto create(ProductPayload payload, MultipartFile[] files, Jwt jwt);

    void delete(UUID id, Jwt jwt) throws ProductNotFoundException;

    ProductDto update(UUID id, ProductUpdatePayload payload, Jwt jwt);

    PageDto<ProductDto> readAll(ProductsFilter filter, int page, int size);

    Product get(UUID id, UUID userId);
}
