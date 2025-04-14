package re1kur.catalogueservice.mapper;

import dto.CategoryDto;
import payload.CategoryPayload;
import re1kur.catalogueservice.entity.Category;

public interface CategoryMapper {
    CategoryDto read(Category category);

    Category write(CategoryPayload payload);
}
