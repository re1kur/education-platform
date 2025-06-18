package re1kur.catalogueservice.mapper;

import dto.CategoryDto;
import org.junit.jupiter.api.Test;
import payload.CategoryPayload;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.mapper.impl.DefaultCategoryMapper;

import static org.junit.jupiter.api.Assertions.*;
class DefaultCategoryMapperTest {
    private final DefaultCategoryMapper mapper = new DefaultCategoryMapper();

    @Test
    void testRead_ReturnsCorrectDto() {
        Category category = Category.builder()
                .id(1)
                .title("Category Title")
                .description("Category Description")
                .imageUrl("http://image.url/image.png")
                .build();

        CategoryDto dto = mapper.read(category);

        assertNotNull(dto);
        assertEquals(category.getId(), dto.id());
        assertEquals(category.getTitle(), dto.title());
        assertEquals(category.getDescription(), dto.description());
        assertEquals(category.getImageUrl(), dto.imageUrl());
    }

    @Test
    void testWrite_ReturnsCategory() {
        CategoryPayload payload = new CategoryPayload("Payload Title", "Payload Description");

        Category category = mapper.write(payload);

        assertNotNull(category);
        assertEquals(payload.title(), category.getTitle());
        assertEquals(payload.description(), category.getDescription());
        assertNull(category.getId());
        assertNull(category.getImageUrl());
    }
}