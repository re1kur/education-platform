package re1kur.catalogueservice.service;

import dto.GoodsDto;
import dto.GoodsPageDto;
import filter.GoodsFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import payload.GoodsPayload;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsAlreadyExistException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.mapper.GoodsMapper;
import re1kur.catalogueservice.repository.GoodsRepository;
import re1kur.catalogueservice.service.impl.DefaultGoodsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultGoodsServiceTest {
    @InjectMocks
    private DefaultGoodsService service;

    @Mock
    private GoodsRepository repo;

    @Mock
    private GoodsMapper mapper;

    @Test
    void testGet__GoodsValid__DoesNotThrowException() {
        Integer goodsId = 1;
        Goods expected = Goods.builder()
                .id(1)
                .title("title")
                .description("description")
                .build();

        GoodsDto expectedDto = new GoodsDto(expected.getId(), expected.getTitle(), null,
                expected.getDescription(), null, false, null);

        Mockito.when(repo.findById(1)).thenReturn(Optional.of(expected));
        Mockito.when(mapper.read(expected)).thenReturn(expectedDto);

        GoodsDto result = Assertions.assertDoesNotThrow(() -> service.get(goodsId));

        Assertions.assertEquals(expectedDto, result);

        Mockito.verify(repo, Mockito.times(1)).findById(goodsId);
        Mockito.verify(mapper, Mockito.times(1)).read(expected);
    }

    @Test
    void testGet__NotExistingGoods__ThrowsGoodsNotFoundException() {
        Integer goodsId = 1;

        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.get(goodsId));

        Mockito.verify(repo, Mockito.times(1)).findById(goodsId);
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void testCreate__GoodsValid__DoesNotThrowException() throws CategoryNotFoundException {
        GoodsPayload payload = new GoodsPayload(
                "title",
                1,
                "description",
                new BigDecimal("123.123"));

        Goods expected = Goods.builder()
                .description("description")
                .title("title")
                .price(new BigDecimal("123.123"))
                .category(Category.builder().id(1).build())
                .build();

        Mockito.when(repo.existsByTitle("title")).thenReturn(false);
        Mockito.when(mapper.write(payload)).thenReturn(expected);

        Assertions.assertDoesNotThrow(() -> service.create(payload));

        Mockito.verify(repo, Mockito.times(1)).existsByTitle("title");
        Mockito.verify(mapper, Mockito.times(1)).write(payload);
    }

    @Test
    void testCreate__GoodsAlreadyExists__ThrowsGoodsAlreadyExistsException() {
        GoodsPayload payload = new GoodsPayload(
                "title",
                1,
                "description",
                new BigDecimal("123.123"));

        Mockito.when(repo.existsByTitle("title")).thenReturn(true);

        Assertions.assertThrows(GoodsAlreadyExistException.class, () -> service.create(payload));

        Mockito.verify(repo, Mockito.times(1)).existsByTitle("title");
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void testCreate__GoodsWithCategoryThatNotExist_ThrowsCategoryNotFoundException() throws CategoryNotFoundException {
        GoodsPayload payload = new GoodsPayload(
                "title",
                1,
                "description",
                new BigDecimal("123.123"));

        Mockito.when(repo.existsByTitle("title")).thenReturn(false);
        Mockito.when(mapper.write(payload)).thenThrow(CategoryNotFoundException.class);

        Assertions.assertThrows(CategoryNotFoundException.class, () -> service.create(payload));

        Mockito.verify(repo, Mockito.times(1)).existsByTitle("title");
        Mockito.verify(mapper, Mockito.times(1)).write(payload);
    }

    @Test
    void testDelete__GoodsValid__DoesNotThrowException() {
        Integer goodsId = 1;

        Mockito.when(repo.existsById(1)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> service.delete(goodsId));

        Mockito.verify(repo, Mockito.times(1)).existsById(1);
        Mockito.verify(repo, Mockito.times(1)).deleteById(1);
    }

    @Test
    void testDelete__NotExistingGoods__ThrowsGoodsNotFoundException() {
        Integer goodsId = 1;

        Mockito.when(repo.existsById(1)).thenReturn(false);

        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.delete(goodsId));

        Mockito.verify(repo, Mockito.times(1)).existsById(1);
        Mockito.verify(repo, Mockito.times(0)).deleteById(1);
    }

    @Test
    void testGetPage_ReturnsMappedPage() {
        Pageable pageable = PageRequest.of(0, 10);
        GoodsFilter filter = Mockito.mock(GoodsFilter.class);
        Mockito.when(filter.categoryId()).thenReturn(1);
        Mockito.when(filter.price()).thenReturn(new BigDecimal("100"));
        Mockito.when(filter.title()).thenReturn("test");

        Goods mapped = new Goods();
        GoodsDto dto = Mockito.mock(GoodsDto.class);

        Page<Goods> entityPage = new PageImpl<>(List.of(mapped), pageable, 1);

        Mockito.when(repo.findAllByFilter(pageable, 1, new BigDecimal("100"), "test")).thenReturn(entityPage);
        Mockito.when(mapper.read(mapped)).thenReturn(dto);

        GoodsPageDto result = service.getPage(pageable, filter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.content().size());
        Assertions.assertEquals(dto, result.content().getFirst());
        Mockito.verify(repo).findAllByFilter(pageable, 1, new BigDecimal("100"), "test");
        Mockito.verify(mapper).read(mapped);
    }

    @Test
    void testGetList_ReturnsMappedList() {
        Goods entity1 = Goods.builder()
                .id(1)
                .build();
        Goods entity2 = Goods.builder()
                .id(2)
                .build();

        GoodsDto dto1 = Mockito.mock(GoodsDto.class);
        GoodsDto dto2 = Mockito.mock(GoodsDto.class);

        Mockito.when(repo.findAll()).thenReturn(List.of(entity1, entity2));
        Mockito.when(mapper.read(entity1)).thenReturn(dto1);
        Mockito.when(mapper.read(entity2)).thenReturn(dto2);

        List<GoodsDto> result = service.getList();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(dto1));
        Assertions.assertTrue(result.contains(dto2));
        Mockito.verify(repo).findAll();
        Mockito.verify(mapper).read(entity1);
        Mockito.verify(mapper).read(entity2);
    }
}