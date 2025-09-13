package com.example.catalogueservice.service.impl;

import com.example.catalogueservice.entity.Category;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.service.CategoryService;
import com.example.catalogueservice.service.FileService;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.exception.ProductConflictException;
import com.example.exception.ProductNotFoundException;
import com.example.filter.ProductsFilter;
import com.example.payload.ProductPayload;
import com.example.payload.ProductUpdatePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.example.catalogueservice.mapper.ProductMapper;
import com.example.catalogueservice.repository.ProductRepository;
import com.example.catalogueservice.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;
    private final ProductMapper mapper;
    private final CategoryService categoryService;
    private final FileService fileService;


    private final String PRODUCT_NOT_FOUND_MESSAGE = "PRODUCT [%s] WAS NOT FOUND.";
    private final String PRODUCT_EXISTS_MESSAGE = "PRODUCT [%s] ALREADY EXISTS.";

    @Override
    public ProductDto read(UUID id) {
        return repo.findById(id).map(mapper::read)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    @Transactional
    public ProductDto create(ProductPayload payload, MultipartFile[] files, Jwt jwt) {
        String title = payload.title();
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("CREATE PRODUCT [{}] REQUEST BY USER [{}]", title, userId);

        if (repo.existsByTitle(title))
            throw new ProductConflictException(PRODUCT_EXISTS_MESSAGE.formatted(title));

        List<UUID> fileIds = fileService.uploadFiles(files);
        Category category = categoryService.get(payload.categoryId(), userId);
        Product product = mapper.create(payload, category, fileIds);

        Product saved = repo.save(product);

        log.info("CREATED PRODUCT [{}] BY USER [{}]", saved.getId(), userId);
        return mapper.read(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("DELETE PRODUCT [{}] REQUEST BY USER [{}]", id, userId);

        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE));

        repo.delete(product);

        log.info("DELETED PRODUCT [{}] BY USER [{}]", id, userId);
    }

    @Override
    public ProductDto update(UUID id, ProductUpdatePayload payload, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        String title = payload.title();
        log.info("UPDATE PRODUCT [{}] BY USER [{}]", id, userId);

        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE));


        checkConflicts(product, title);
        Product mapped = mapper.update(product, payload);

        Product saved = repo.save(mapped);

        return mapper.read(saved);
    }

    private void checkConflicts(Product product, String title) {
        if (!product.getTitle().equals(title)) {
            if (repo.existsByTitle(title))
                throw new ProductConflictException(PRODUCT_EXISTS_MESSAGE.formatted(title));
        }
    }

    @Override
    public PageDto<ProductDto> readAll(ProductsFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        UUID categoryId = filter.categoryId();
        Integer price = filter.price();
        String title = filter.title();
        Boolean single = filter.single();

        Page<Product> productPage = repo.findAll(pageable, categoryId, price, single, title);
        return mapper.readPage(productPage);
    }

    @Override
    public Product get(UUID id, UUID userId) {
        log.info("GET PRODUCT [{}] REQUEST BY USER [{}]", id, userId);
        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE));
    }
}
