//package com.example.catalogueservice.service;
//
//import com.example.catalogueservice.entity.Product;
//import dto.GoodsDto;
//import dto.GoodsPageDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import payload.UpdateCatalogueGoodsPayload;
//import com.example.catalogueservice.entity.CatalogueProduct;
//import com.example.catalogueservice.entity.Category;
//import com.example.catalogueservice.exception.CatalogueGoodsAlreadyExistException;
//import com.example.catalogueservice.exception.GoodsNotFoundException;
//import com.example.catalogueservice.mapper.impl.DefaultGoodsMapper;
//import com.example.catalogueservice.repository.CatalogueRepository;
//import com.example.catalogueservice.repository.GoodsRepository;
//import com.example.catalogueservice.service.impl.DefaultCatalogueService;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//@ExtendWith(MockitoExtension.class)
//class DefaultCatalogueServiceTest {
//    @InjectMocks
//    private DefaultCatalogueService service;
//
//    @Mock
//    private CatalogueRepository repo;
//
//    @Mock
//    private GoodsRepository goodsRepo;
//
//    @Mock
//    private DefaultGoodsMapper mapper;
//
//    private final Integer GOODS_ID = 1;
//    private final Integer NEW_ORDER = 42;
//
//    @Test
//    void testGetCatalogue() {
//        Category category = Category.builder()
//                .title("title")
//                .id(GOODS_ID)
//                .build();
//
//        Product product1 = Product.builder().id(1).title("title").description("description")
//                .price(new BigDecimal("123.45")).category(category).inStock(true).imageUrl("imageUrl").build();
//        Product product2 = Product.builder().id(2).title("title").description("description")
//                .price(new BigDecimal("123.45")).category(category).inStock(true).imageUrl("imageUrl").build();
//        Product product3 = Product.builder().id(3).title("title").description("description")
//                .price(new BigDecimal("123.45")).category(category).inStock(true).imageUrl("imageUrl").build();
//
//        Pageable pageable = PageRequest.of(0, 4);
//
//        Page<CatalogueProduct> cataloguePageMock = Mockito.mock(Page.class);
//        Mockito.when(repo.findAllAndOrderByOrder(pageable)).thenReturn(cataloguePageMock);
//
//        Page<GoodsDto> expectedDtos = getGoodsDtos(product1, product2, product3);
//        GoodsPageDto expectedPage = GoodsPageDto.of(expectedDtos);
//
//        Mockito.when(cataloguePageMock.map(Mockito.any(Function.class))).thenReturn(expectedDtos);
//
//        GoodsPageDto result = service.getCatalogueGoods(pageable);
//
//        Assertions.assertEquals(expectedPage, result);
//        Mockito.verify(repo, Mockito.times(1)).findAllAndOrderByOrder(pageable);
//    }
//
//    @Test
//    void testAddValidGoodsToCatalogue() {
//        CatalogueProduct expected = CatalogueProduct.builder()
//                .goodsId(GOODS_ID)
//                .build();
//
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
//        Mockito.when(repo.existsById(GOODS_ID)).thenReturn(false);
//
//        Assertions.assertDoesNotThrow(() -> service.addGoodsToCatalogue(GOODS_ID));
//
//        Mockito.verify(goodsRepo, Mockito.times(1)).existsById(GOODS_ID);
//        Mockito.verify(repo, Mockito.times(1)).existsById(GOODS_ID);
//        Mockito.verify(repo, Mockito.times(1)).save(expected);
//    }
//
//    @Test
//    void testAddNotExistingGoodsToCatalogue() {
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(false);
//
//        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.addGoodsToCatalogue(GOODS_ID));
//
//        Mockito.verifyNoInteractions(repo);
//    }
//
//    @Test
//    void testAddGoodsThatAlreadyInCatalogueToCatalogue() {
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
//
//        Mockito.when(repo.existsById(GOODS_ID)).thenReturn(true);
//
//        Assertions.assertThrows(CatalogueGoodsAlreadyExistException.class, () -> service.addGoodsToCatalogue(GOODS_ID));
//
//        Mockito.verify(repo, Mockito.times(1)).existsById(GOODS_ID);
//        Mockito.verify(repo, Mockito.times(0)).save(Mockito.any(CatalogueProduct.class));
//    }
//
//    @Test
//    void testRemoveValidGoodsFromCatalogue() {
//        CatalogueProduct expected = CatalogueProduct.builder()
//                .goodsId(GOODS_ID)
//                .build();
//
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
//        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.of(expected));
//
//        Assertions.assertDoesNotThrow(() -> service.removeGoodsFromCatalogue(GOODS_ID));
//
//        Mockito.verify(repo, Mockito.times(1)).delete(expected);
//    }
//
//    @Test
//    void testRemoveNotExistingGoodsFromCatalogue() {
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(false);
//
//        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.removeGoodsFromCatalogue(GOODS_ID));
//
//        Mockito.verifyNoInteractions(repo);
//    }
//
//    @Test
//    void testRemoveGoodsThatAreNotInCatalogue_throwsException() {
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
//        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(CatalogueGoodsAlreadyExistException.class, () -> service.removeGoodsFromCatalogue(GOODS_ID));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(GOODS_ID);
//        Mockito.verify(repo, Mockito.times(0)).delete(Mockito.any(CatalogueProduct.class));
//    }
//
//    @Test
//    void testUpdate_ValidPayload_UpdatesOrderAndSaves() {
//        UpdateCatalogueGoodsPayload payload = new UpdateCatalogueGoodsPayload(GOODS_ID, NEW_ORDER);
//
//        CatalogueProduct catalogueProduct = new CatalogueProduct();
//        catalogueProduct.setGoodsId(GOODS_ID);
//        catalogueProduct.setOrder(1);
//
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
//        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.of(catalogueProduct));
//
//        Assertions.assertDoesNotThrow(() -> service.updateGoodsInCatalogue(payload));
//
//        Assertions.assertEquals(NEW_ORDER, catalogueProduct.getOrder());
//
//        Mockito.verify(repo, Mockito.times(1)).save(catalogueProduct);
//    }
//
//    @Test
//    void testUpdate_GoodsDoesNotExist_ThrowsGoodsNotFoundException() {
//        UpdateCatalogueGoodsPayload payload = new UpdateCatalogueGoodsPayload(GOODS_ID, NEW_ORDER);
//
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(false);
//
//        Assertions.assertThrows(GoodsNotFoundException.class, () -> service.updateGoodsInCatalogue(payload));
//        Mockito.verifyNoInteractions(repo);
//    }
//
//    @Test
//    void testUpdate_CatalogueEntryDoesNotExist_ThrowsCatalogueGoodsExistingException() {
//        UpdateCatalogueGoodsPayload payload = new UpdateCatalogueGoodsPayload(GOODS_ID, NEW_ORDER);
//
//        Mockito.when(goodsRepo.existsById(GOODS_ID)).thenReturn(true);
//        Mockito.when(repo.findById(GOODS_ID)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(CatalogueGoodsAlreadyExistException.class, () -> service.updateGoodsInCatalogue(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(GOODS_ID);
//        Mockito.verifyNoMoreInteractions(repo);
//    }
//
//    private static Page<GoodsDto> getGoodsDtos(Product product1, Product product2, Product product3) {
//        GoodsDto dto1 = new GoodsDto(product1.getId(), product1.getTitle(), product1.getCategory().getId(),
//                product1.getDescription(), product1.getPrice(), product1.getInStock(), product1.getImageUrl());
//        GoodsDto dto2 = new GoodsDto(product2.getId(), product2.getTitle(), product2.getCategory().getId(),
//                product2.getDescription(), product2.getPrice(), product2.getInStock(), product2.getImageUrl());
//        GoodsDto dto3 = new GoodsDto(product3.getId(), product3.getTitle(), product3.getCategory().getId(),
//                product3.getDescription(), product3.getPrice(), product3.getInStock(), product3.getImageUrl());
//
//        return new PageImpl<>(List.of(dto1, dto2, dto3));
//    }
//}