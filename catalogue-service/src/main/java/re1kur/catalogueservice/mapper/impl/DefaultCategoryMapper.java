package re1kur.catalogueservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dto.CategoryDto;
import payload.CategoryPayload;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.mapper.CategoryMapper;

@Component
@RequiredArgsConstructor
public class DefaultCategoryMapper implements CategoryMapper {

    @Override
    public CategoryDto read(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getImageUrl()
        );
    }

    @Override
    public Category write(CategoryPayload payload) {
        return Category.builder()
                .title(payload.title())
                .description(payload.description())
                .build();
    }
}
