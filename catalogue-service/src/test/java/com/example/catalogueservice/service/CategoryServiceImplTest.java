//package com.example.catalogueservice.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import payload.CategoryPayload;
//import payload.CategoryUpdatePayload;
//import com.example.catalogueservice.entity.Category;
//import com.example.exception.CategoryConflictException;
//import com.example.exception.CategoryNotFoundException;
//import com.example.catalogueservice.mapper.CategoryMapper;
//import com.example.catalogueservice.repository.CategoryRepository;
//import com.example.catalogueservice.service.impl.CategoryServiceImpl;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class CategoryServiceImplTest {
//    @InjectMocks
//    private CategoryServiceImpl service;
//
//    @Mock
//    private CategoryRepository repo;
//
//    @Mock
//    private CategoryMapper mapper;
//
//    @Test
//    void testGetValidCategory() {
//        Integer categoryId = 1;
//        Category expectedCategory = Category.builder()
//                .id(1)
//                .title("title")
//                .description("description")
//                .imageUrl("imageUrl")
//                .build();
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(expectedCategory));
//
//        Assertions.assertDoesNotThrow(() -> service.get(categoryId));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void testGetNotExistingCategory() {
//        Integer categoryId = 1;
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(CategoryNotFoundException.class, () -> service.get(categoryId));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verifyNoInteractions(mapper);
//    }
//
//    @Test
//    void testCreateValidCategory() {
//        CategoryPayload payload = new CategoryPayload("title", "description");
//        Category expected = Category.builder()
//                .title("title")
//                .description("description")
//                .build();
//
//        Mockito.when(repo.existsByTitle("title")).thenReturn(false);
//        Mockito.when(mapper.write(new CategoryPayload("title", "description"))).thenReturn(expected);
//
//        Assertions.assertDoesNotThrow(() -> service.create(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).save(expected);
//    }
//
//    @Test
//    void testCreateAlreadyExistingCategory() {
//        CategoryPayload payload = new CategoryPayload("title", "description");
//
//        Mockito.when(repo.existsByTitle("title")).thenReturn(true);
//
//        Assertions.assertThrows(CategoryConflictException.class, () -> service.create(payload));
//
//        Mockito.verifyNoInteractions(mapper);
//    }
//
//    @Test
//    void testDelete__CategoryValid() {
//        Integer categoryId = 1;
//        Category expected = Category.builder()
//                .id(1)
//                .build();
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(expected));
//
//        Assertions.assertDoesNotThrow(() -> service.delete(categoryId));
//
//        Mockito.verify(repo, Mockito.times(1)).delete(expected);
//    }
//
//    @Test
//    void testDelete__CategoryNotExist__ThrowsCategoryNotFoundException() {
//        Integer categoryId = 1;
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(CategoryNotFoundException.class, () -> service.delete(categoryId));
//
//        Mockito.verify(repo, Mockito.times(0)).delete(Mockito.any(Category.class));
//    }
//
//    @Test
//    void testUpdate__CategoryValid__DoesNotThrows() {
//        CategoryUpdatePayload payload = new CategoryUpdatePayload(1, "title", "description");
//
//        Category expected = Category.builder()
//                .id(1)
//                .title("title")
//                .description("description")
//                .build();
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(expected));
//
//        Assertions.assertDoesNotThrow(() -> service.update(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void testUpdate__NotExistingCategory__ThrowsCategoryNotFoundException() {
//        CategoryUpdatePayload payload = new CategoryUpdatePayload(1, "title", "description");
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(CategoryNotFoundException.class, () -> service.update(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//    }
//}
