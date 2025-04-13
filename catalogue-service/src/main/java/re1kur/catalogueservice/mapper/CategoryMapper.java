package re1kur.catalogueservice.mapper;

import re1kur.catalogueservice.dto.CategoryDto;
import re1kur.catalogueservice.dto.CategoryPayload;
import re1kur.catalogueservice.entity.Category;

public interface CategoryMapper {
    CategoryDto read(Category category);

    Category write(CategoryPayload payload);
}
