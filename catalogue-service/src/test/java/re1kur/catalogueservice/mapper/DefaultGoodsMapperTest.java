package re1kur.catalogueservice.mapper;

import dto.GoodsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.mapper.impl.DefaultGoodsMapper;
import re1kur.catalogueservice.repository.CategoryRepository;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultGoodsMapperTest {
    @InjectMocks
    private DefaultGoodsMapper mapper;

    @Mock
    private CategoryRepository repo;

    @Test
    void testRead_ReturnsCorrectDto() {
        Category category = new Category();
        category.setId(10);

        Goods goods = new Goods();
        goods.setId(1);
        goods.setTitle("Test Goods");
        goods.setCategory(category);
        goods.setDescription("Description");
        goods.setPrice(new BigDecimal("123.45"));
        goods.setInStock(true);
        goods.setImageUrl("http://image.url");

        GoodsDto dto = mapper.read(goods);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(goods.getId(), dto.id());
        Assertions.assertEquals(goods.getTitle(), dto.title());
        Assertions.assertEquals(category.getId(), dto.categoryId());
        Assertions.assertEquals(goods.getDescription(), dto.description());
        Assertions.assertEquals(goods.getPrice(), dto.price());
        Assertions.assertEquals(goods.getInStock(), dto.inStock());
        Assertions.assertEquals(goods.getImageUrl(), dto.imageUrl());
    }

    @Test
    void testWrite_ReturnsGoods_WhenCategoryExists() throws CategoryNotFoundException {
        Integer categoryId = 10;
        GoodsPayload payload = Mockito.mock(GoodsPayload.class);
        Mockito.when(payload.categoryId()).thenReturn(categoryId);
        Mockito.when(payload.title()).thenReturn("Title");
        Mockito.when(payload.price()).thenReturn(new BigDecimal("99.99"));
        Mockito.when(payload.description()).thenReturn("Desc");

        Category category = new Category();
        category.setId(categoryId);

        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        Goods goods = mapper.write(payload);

        Assertions.assertNotNull(goods);
        Assertions.assertEquals(payload.title(), goods.getTitle());
        Assertions.assertEquals(category, goods.getCategory());
        Assertions.assertEquals(payload.price(), goods.getPrice());
        Assertions.assertEquals(payload.description(), goods.getDescription());
    }

    @Test
    void testWrite_ThrowsException_WhenCategoryNotFound() {
        Integer categoryId = 10;
        GoodsPayload payload = Mockito.mock(GoodsPayload.class);
        Mockito.when(payload.categoryId()).thenReturn(categoryId);

        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException ex = Assertions.assertThrows(CategoryNotFoundException.class, () ->
                mapper.write(payload));

        Assertions.assertTrue(ex.getMessage().contains(String.valueOf(categoryId)));
    }

    @Test
    void testUpdate_ReturnsGoods_WhenCategoryExists() throws CategoryNotFoundException {
        Integer categoryId = 20;
        GoodsUpdatePayload payload = Mockito.mock(GoodsUpdatePayload.class);
        Mockito.when(payload.categoryId()).thenReturn(categoryId);
        Mockito.when(payload.id()).thenReturn(100);
        Mockito.when(payload.title()).thenReturn("Updated Title");
        Mockito.when(payload.description()).thenReturn("Updated Desc");
        Mockito.when(payload.price()).thenReturn(new BigDecimal("199.99"));
        Mockito.when(payload.inStock()).thenReturn(true);

        Category category = new Category();
        category.setId(categoryId);

        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.of(category));

        Goods goods = mapper.update(payload);

        Assertions.assertNotNull(goods);
        Assertions.assertEquals(payload.id(), goods.getId());
        Assertions.assertEquals(category, goods.getCategory());
        Assertions.assertEquals(payload.title(), goods.getTitle());
        Assertions.assertEquals(payload.description(), goods.getDescription());
        Assertions.assertEquals(payload.price(), goods.getPrice());
        Assertions.assertEquals(payload.inStock(), goods.getInStock());
    }

    @Test
    void testUpdate_ThrowsException_WhenCategoryNotFound() {
        Integer categoryId = 20;
        GoodsUpdatePayload payload = Mockito.mock(GoodsUpdatePayload.class);
        Mockito.when(payload.categoryId()).thenReturn(categoryId);

        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException ex = Assertions.assertThrows(CategoryNotFoundException.class, () ->
                mapper.update(payload)
        );

        Assertions.assertTrue(ex.getMessage().contains(String.valueOf(categoryId)));
    }
}