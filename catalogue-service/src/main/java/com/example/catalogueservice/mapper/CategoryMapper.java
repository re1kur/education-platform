package com.example.catalogueservice.mapper;

import com.example.dto.CategoryDto;
import com.example.dto.PageDto;
import com.example.payload.CategoryPayload;
import com.example.catalogueservice.entity.Category;
import com.example.payload.CategoryUpdatePayload;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CategoryMapper {
    CategoryDto read(Category category);

    Category create(CategoryPayload payload, UUID fileId);

    PageDto<CategoryDto> readPage(Page<Category> page);

    Category update(Category found, CategoryUpdatePayload payload);
}
