package com.example.catalogueservice.service;

import com.example.catalogueservice.entity.Category;
import com.example.dto.CategoryDto;
import com.example.dto.PageDto;
import com.example.filter.CategoriesFilter;
import com.example.payload.CategoryPayload;
import com.example.payload.CategoryUpdatePayload;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CategoryService {
    CategoryDto read(UUID id);

    void delete(UUID id, Jwt jwt);

    CategoryDto update(UUID id, CategoryUpdatePayload payload, Jwt jwt);

    PageDto<CategoryDto> readAll(CategoriesFilter filter, int page, int size);

    CategoryDto create(CategoryPayload payload, MultipartFile titleImage, Jwt jwt);

    Category get(UUID categoryId, UUID userId);
}
