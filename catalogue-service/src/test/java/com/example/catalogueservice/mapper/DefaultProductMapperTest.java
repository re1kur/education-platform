//package com.example.catalogueservice.mapper;
//
//import com.example.catalogueservice.entity.Product;
//import dto.GoodsDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import payload.GoodsPayload;
//import payload.GoodsUpdatePayload;
//import com.example.catalogueservice.entity.Category;
//import com.example.catalogueservice.exception.CategoryNotFoundException;
//import com.example.catalogueservice.mapper.impl.DefaultGoodsMapper;
//import com.example.catalogueservice.repository.CategoryRepository;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//class DefaultProductMapperTest {
//    @InjectMocks
//    private DefaultGoodsMapper mapper;
//
//    @Mock
//    private CategoryRepository repo;
//
//    @Test
//    void testRead_ReturnsCorrectDto() {
//        Category category = new Category();
//        category.setId(10);
//
//        Product product = new Product();
//        product.setId(1);
//        product.setTitle("Test Goods");
//        product.setCategory(category);
//        product.setDescription("Description");
//        product.setPrice(new BigDecimal("123.45"));
//        product.setInStock(true);
//        product.setImageUrl("http://image.url");
//
//        GoodsDto dto = mapper.read(product);
//
//        Assertions.assertNotNull(dto);
//        Assertions.assertEquals(product.getId(), dto.id());
//        Assertions.assertEquals(product.getTitle(), dto.title());
//        Assertions.assertEquals(category.getId(), dto.categoryId());
//        Assertions.assertEquals(product.getDescription(), dto.description());
//        Assertions.assertEquals(product.getPrice(), dto.price());
//        Assertions.assertEquals(product.getInStock(), dto.inStock());
//        Assertions.assertEquals(product.getImageUrl(), dto.imageUrl());
//    }
//
//    @Test
//    void testWrite_ReturnsGoods_WhenCategoryExists() throws CategoryNotFoundException {
//        Integer categoryId = 10;
//        GoodsPayload payload = Mockito.mock(GoodsPayload.class);
//        Mockito.when(payload.categoryId()).thenReturn(categoryId);
//        Mockito.when(payload.title()).thenReturn("Title");
//        Mockito.when(payload.price()).thenReturn(new BigDecimal("99.99"));
//        Mockito.when(payload.description()).thenReturn("Desc");
//
//        Category category = new Category();
//        category.setId(categoryId);
//
//        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.of(category));
//
//        Product product = mapper.write(payload);
//
//        Assertions.assertNotNull(product);
//        Assertions.assertEquals(payload.title(), product.getTitle());
//        Assertions.assertEquals(category, product.getCategory());
//        Assertions.assertEquals(payload.price(), product.getPrice());
//        Assertions.assertEquals(payload.description(), product.getDescription());
//    }
//
//    @Test
//    void testWrite_ThrowsException_WhenCategoryNotFound() {
//        Integer categoryId = 10;
//        GoodsPayload payload = Mockito.mock(GoodsPayload.class);
//        Mockito.when(payload.categoryId()).thenReturn(categoryId);
//
//        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.empty());
//
//        CategoryNotFoundException ex = Assertions.assertThrows(CategoryNotFoundException.class, () ->
//                mapper.write(payload));
//
//        Assertions.assertTrue(ex.getMessage().contains(String.valueOf(categoryId)));
//    }
//
//    @Test
//    void testUpdate_ReturnsGoods_WhenCategoryExists() throws CategoryNotFoundException {
//        Integer categoryId = 20;
//        GoodsUpdatePayload payload = Mockito.mock(GoodsUpdatePayload.class);
//        Mockito.when(payload.categoryId()).thenReturn(categoryId);
//        Mockito.when(payload.id()).thenReturn(100);
//        Mockito.when(payload.title()).thenReturn("Updated Title");
//        Mockito.when(payload.description()).thenReturn("Updated Desc");
//        Mockito.when(payload.price()).thenReturn(new BigDecimal("199.99"));
//        Mockito.when(payload.inStock()).thenReturn(true);
//
//        Category category = new Category();
//        category.setId(categoryId);
//
//        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.of(category));
//
//        Product product = mapper.update(payload);
//
//        Assertions.assertNotNull(product);
//        Assertions.assertEquals(payload.id(), product.getId());
//        Assertions.assertEquals(category, product.getCategory());
//        Assertions.assertEquals(payload.title(), product.getTitle());
//        Assertions.assertEquals(payload.description(), product.getDescription());
//        Assertions.assertEquals(payload.price(), product.getPrice());
//        Assertions.assertEquals(payload.inStock(), product.getInStock());
//    }
//
//    @Test
//    void testUpdate_ThrowsException_WhenCategoryNotFound() {
//        Integer categoryId = 20;
//        GoodsUpdatePayload payload = Mockito.mock(GoodsUpdatePayload.class);
//        Mockito.when(payload.categoryId()).thenReturn(categoryId);
//
//        Mockito.when(repo.findById(categoryId)).thenReturn(Optional.empty());
//
//        CategoryNotFoundException ex = Assertions.assertThrows(CategoryNotFoundException.class, () ->
//                mapper.update(payload)
//        );
//
//        Assertions.assertTrue(ex.getMessage().contains(String.valueOf(categoryId)));
//    }
//}