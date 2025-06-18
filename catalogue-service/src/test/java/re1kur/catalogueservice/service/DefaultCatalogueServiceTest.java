package re1kur.catalogueservice.service;

import dto.GoodsDto;
import dto.GoodsPageDto;
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
import payload.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.entity.CatalogueGoods;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CatalogueGoodsAlreadyExistException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.mapper.impl.DefaultGoodsMapper;
import re1kur.catalogueservice.repository.CatalogueRepository;
import re1kur.catalogueservice.repository.GoodsRepository;
import re1kur.catalogueservice.service.impl.DefaultCatalogueService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class DefaultCatalogueServiceTest {
    @InjectMocks
    private DefaultCatalogueService service;

    @Mock
    private CatalogueRepository repo;

    @Mock
    private GoodsRepository goodsRepo;

    @Mock
    private DefaultGoodsMapper mapper;

    private final Integer GOODS_ID = 1;
    private final Integer NEW_ORDER = 42;

    @Test
    void testGetCatalogue() {
        Category category = Category.builder()
                .title("title")
                .id(GOODS_ID)
                .build();

        Goods goods1 = Goods.builder().id(1).title("title").description("description")
                .price(new BigDecimal("123.45")).category(category).inStock(true).imageUrl("imageUrl").build();
        Goods goods2 = Goods.builder().id(2).title("title").description("description")
                .price(new BigDecimal("123.45")).category(category).inStock(true).imageUrl("imageUrl").build();
        Goods goods3 = Goods.builder().id(3).title("title").description("description")
                .price(new BigDecimal("123.45")).category(category).inStock(true).imageUrl("imageUrl").build();

        Pageable pageable = PageRequest.of(0, 4);

        Page<CatalogueGoods> cataloguePageMock = Mockito.mock(Page.class);
        Mockito.when(repo.findAllAndOrderByOrder(pageable)).thenReturn(cataloguePageMock);

        Page<GoodsDto> expectedDtos = getGoodsDtos(goods1, goods2, goods3);
        GoodsPageDto expectedPage = GoodsPageDto.of(expectedDtos);

        Mockito.when(cataloguePageMock.map(Mockito.any(Function.class))).thenReturn(expectedDtos);

        GoodsPageDto result = service.getCatalogueGoods(pageable);

        Assertions.assertEquals(expectedPage, result);
        Mockito.verify(repo, Mockito.times(1)).findAllAndOrderByOrder(pageable);
    }

    @Test
    void testAddValidGoodsToCatalogue() {
        CatalogueGoods expected = CatalogueGoods.builder()
                .goodsId(GOODS_ID)
                .build();

        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
        Mockito.when(repo.existsById(GOODS_ID)).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> service.addGoodsToCatalogue(GOODS_ID));

        Mockito.verify(goodsRepo, Mockito.times(1)).existsById(GOODS_ID);
        Mockito.verify(repo, Mockito.times(1)).existsById(GOODS_ID);
        Mockito.verify(repo, Mockito.times(1)).save(expected);
    }

    @Test
    void testAddNotExistingGoodsToCatalogue() {
        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(false);

        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.addGoodsToCatalogue(GOODS_ID));

        Mockito.verifyNoInteractions(repo);
    }

    @Test
    void testAddGoodsThatAlreadyInCatalogueToCatalogue() {
        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);

        Mockito.when(repo.existsById(GOODS_ID)).thenReturn(true);

        Assertions.assertThrows(CatalogueGoodsAlreadyExistException.class, () -> service.addGoodsToCatalogue(GOODS_ID));

        Mockito.verify(repo, Mockito.times(1)).existsById(GOODS_ID);
        Mockito.verify(repo, Mockito.times(0)).save(Mockito.any(CatalogueGoods.class));
    }

    @Test
    void testRemoveValidGoodsFromCatalogue() {
        CatalogueGoods expected = CatalogueGoods.builder()
                .goodsId(GOODS_ID)
                .build();

        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.of(expected));

        Assertions.assertDoesNotThrow(() -> service.removeGoodsFromCatalogue(GOODS_ID));

        Mockito.verify(repo, Mockito.times(1)).delete(expected);
    }

    @Test
    void testRemoveNotExistingGoodsFromCatalogue() {
        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(false);

        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.removeGoodsFromCatalogue(GOODS_ID));

        Mockito.verifyNoInteractions(repo);
    }

    @Test
    void testRemoveGoodsThatAreNotInCatalogue_throwsException() {
        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(CatalogueGoodsAlreadyExistException.class, () -> service.removeGoodsFromCatalogue(GOODS_ID));

        Mockito.verify(repo, Mockito.times(1)).findById(GOODS_ID);
        Mockito.verify(repo, Mockito.times(0)).delete(Mockito.any(CatalogueGoods.class));
    }

    @Test
    void testUpdate_ValidPayload_UpdatesOrderAndSaves() {
        UpdateCatalogueGoodsPayload payload = new UpdateCatalogueGoodsPayload(GOODS_ID, NEW_ORDER);

        CatalogueGoods catalogueGoods = new CatalogueGoods();
        catalogueGoods.setGoodsId(GOODS_ID);
        catalogueGoods.setOrder(1);

        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.of(catalogueGoods));

        Assertions.assertDoesNotThrow(() -> service.updateGoodsInCatalogue(payload));

        Assertions.assertEquals(NEW_ORDER, catalogueGoods.getOrder());

        Mockito.verify(repo, Mockito.times(1)).save(catalogueGoods);
    }

    @Test
    void testUpdate_GoodsDoesNotExist_ThrowsGoodsNotFoundException() {
        UpdateCatalogueGoodsPayload payload = new UpdateCatalogueGoodsPayload(GOODS_ID, NEW_ORDER);

        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(false);

        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.updateGoodsInCatalogue(payload));
        Mockito.verifyNoInteractions(repo);
    }

    @Test
    void testUpdate_CatalogueEntryDoesNotExist_ThrowsCatalogueGoodsExistingException() {
        UpdateCatalogueGoodsPayload payload = new UpdateCatalogueGoodsPayload(GOODS_ID, NEW_ORDER);

        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(CatalogueGoodsAlreadyExistException.class, () -> service.updateGoodsInCatalogue(payload));

        Mockito.verify(repo, Mockito.times(1)).findById(GOODS_ID);
        Mockito.verifyNoMoreInteractions(repo);
    }

    private static Page<GoodsDto> getGoodsDtos(Goods goods1, Goods goods2, Goods goods3) {
        GoodsDto dto1 = new GoodsDto(goods1.getId(), goods1.getTitle(), goods1.getCategory().getId(),
                goods1.getDescription(), goods1.getPrice(), goods1.getInStock(), goods1.getImageUrl());
        GoodsDto dto2 = new GoodsDto(goods2.getId(), goods2.getTitle(), goods2.getCategory().getId(),
                goods2.getDescription(), goods2.getPrice(), goods2.getInStock(), goods2.getImageUrl());
        GoodsDto dto3 = new GoodsDto(goods3.getId(), goods3.getTitle(), goods3.getCategory().getId(),
                goods3.getDescription(), goods3.getPrice(), goods3.getInStock(), goods3.getImageUrl());

        return new PageImpl<>(List.of(dto1, dto2, dto3));
    }
}