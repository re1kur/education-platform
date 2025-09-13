package com.example.catalogueservice.service.impl;

import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.mapper.CatalogueMapper;
import com.example.catalogueservice.mapper.ProductMapper;
import com.example.catalogueservice.service.ProductService;
import com.example.dto.CatalogueProductDto;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.exception.CatalogueProductConflictException;
import com.example.exception.CatalogueProductNotFoundException;
import com.example.payload.CatalogueProductPayload;
import com.example.payload.UpdateCataloguePayloadPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import com.example.catalogueservice.entity.CatalogueProduct;
import com.example.catalogueservice.repository.CatalogueRepository;
import com.example.catalogueservice.service.CatalogueService;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogueServiceImpl implements CatalogueService {
    private final CatalogueRepository repo;
    private final CatalogueMapper mapper;
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final String CATALOGUE_PRODUCT_NOT_FOUND_MESSAGE = "CATALOGUE PRODUCT [%s] WAS NOT FOUND.";

    @Override
    @Transactional
    public CatalogueProductDto create(CatalogueProductPayload payload, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        UUID productId = payload.productId();
        log.info("CREATE CATALOGUE PRODUCT [{}] BY USER [{}]", productId, userId);

        Product found = productService.get(productId, userId);

        if (repo.existsById(productId))
            throw new CatalogueProductConflictException("CATALOGUE PRODUCT [%s] ALREADY EXISTS.".formatted(productId));

        CatalogueProduct mapped = mapper.create(payload, found);

        CatalogueProduct saved = repo.save(mapped);

        log.info("CREATED CATALOGUE PRODUCT [{}] BY USER [{}]", saved.getProduct().getId(), userId);

        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("DELETE CATALOGUE PRODUCT [{}] REQUEST BY USER [{}]", id, userId);

        CatalogueProduct found = repo.findById(id)
                .orElseThrow(() -> new CatalogueProductNotFoundException(CATALOGUE_PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
        repo.delete(found);

        log.info("DELETED CATALOGUE PRODUCT [{}] BY USER [{}]", id, userId);
    }

    @Override
    public CatalogueProductDto update(UUID id, UpdateCataloguePayloadPayload payload, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("UPDATE CATALOGUE PRODUCT [{}] REQUEST BY USER [{}]", id, userId);

        CatalogueProduct found = repo.findById(id)
                .orElseThrow(() -> new CatalogueProductNotFoundException(CATALOGUE_PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
        CatalogueProduct mapped = mapper.update(found, payload);

        CatalogueProduct saved = repo.save(mapped);

        log.info("UPDATED CATALOGUE PRODUCT [{}] BY USER [{}]", saved.getProduct().getId(), userId);
        return mapper.read(saved);
    }

    @Override
    public PageDto<ProductDto> readAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> catalogueProducts = repo.findAll(pageable).map(CatalogueProduct::getProduct);
        return productMapper.readPage(catalogueProducts);
    }
}
