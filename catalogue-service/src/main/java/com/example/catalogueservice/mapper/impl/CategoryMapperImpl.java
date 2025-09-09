package com.example.catalogueservice.mapper.impl;

import com.example.dto.CategoryDto;
import com.example.dto.PageDto;
import com.example.payload.CategoryPayload;
import com.example.payload.CategoryUpdatePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.example.catalogueservice.entity.Category;
import com.example.catalogueservice.mapper.CategoryMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto read(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getPreviewDescription(),
                category.getTitleImageId()
        );
    }

    @Override
    public Category create(CategoryPayload payload, UUID fileId) {
        return Category.builder()
                .title(payload.title())
                .previewDescription(payload.previewDescription())
                .description(payload.description())
                .titleImageId(fileId)
                .build();
    }

    @Override
    public PageDto<CategoryDto> readPage(Page<Category> page) {
        return new PageDto<>(
                page.stream().map(this::read).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Override
    public Category update(Category found, CategoryUpdatePayload payload) {
        found.setTitle(payload.title());
        found.setDescription(payload.description());
        found.setPreviewDescription(payload.previewDescription());

        return found;
    }
}
