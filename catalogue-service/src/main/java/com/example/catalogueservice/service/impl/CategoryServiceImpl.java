package com.example.catalogueservice.service.impl;

import com.example.catalogueservice.service.FileService;
import com.example.dto.CategoryDto;
import com.example.dto.PageDto;
import com.example.filter.CategoriesFilter;
import com.example.payload.CategoryPayload;
import com.example.payload.CategoryUpdatePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.catalogueservice.entity.Category;
import com.example.catalogueservice.exception.CategoryConflictException;
import com.example.catalogueservice.exception.CategoryNotFoundException;
import com.example.catalogueservice.mapper.CategoryMapper;
import com.example.catalogueservice.repository.CategoryRepository;
import com.example.catalogueservice.service.CategoryService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;
    private final CategoryMapper mapper;
    private final FileService fileService;


    private final String CATEGORY_NOT_FOUND_MESSAGE = "CATEGORY [%s] WAS NOT FOUND.";

    @Override
    public CategoryDto read(UUID id) {
        return repo.findById(id)
                .map(mapper::read)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    @Transactional
    public void delete(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("DELETE CATEGORY [{}] REQUEST BY USER [{}]", id, userId);

        Category found = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.formatted(id)));
        repo.delete(found);

        log.info("DELETED CATEGORY [{}] BY USER [{}]", id, userId);
    }

    @Override
    @Transactional
    public CategoryDto update(UUID id, CategoryUpdatePayload payload, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        log.info("UPDATE CATEGORY [{}] REQUEST BY USER [{}]", id, userId);

        Category found = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MESSAGE.formatted(id)));
        Category mapped = mapper.update(found, payload);

        Category saved = repo.save(mapped);

        log.info("UPDATED CATEGORY [{}] BY USER [{}]", id, userId);
        return mapper.read(saved);
    }

    @Override
    public PageDto<CategoryDto> readAll(CategoriesFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        String title = filter.title();

        Page<Category> foundPage = repo.findAll(pageable, title);

        return mapper.readPage(foundPage);
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryPayload payload, MultipartFile titleImage, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        String title = payload.title();
        log.info("CREATE CATEGORY [{}] REQUEST BY USER [{}]", title, userId);

        if (repo.existsByTitle(title))
            throw new CategoryConflictException("Category with title %s is already exists.".formatted(title));

        UUID fileId = fileService.uploadFile(titleImage);
        Category mapped = mapper.create(payload, fileId);

        Category saved = repo.save(mapped);

        log.info("CREATED CATEGORY [{}] BY USER [{}]", saved.getId(), userId);

        return mapper.read(saved);
    }
}
