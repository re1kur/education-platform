package com.example.catalogueservice.service.impl;

import dto.GoodsPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import dto.GoodsDto;
import payload.UpdateCatalogueGoodsPayload;
import com.example.catalogueservice.entity.CatalogueProduct;
import com.example.catalogueservice.exception.CatalogueGoodsAlreadyExistException;
import com.example.catalogueservice.exception.GoodsNotFoundException;
import com.example.catalogueservice.mapper.GoodsMapper;
import com.example.catalogueservice.repository.CatalogueRepository;
import com.example.catalogueservice.repository.GoodsRepository;
import com.example.catalogueservice.service.CatalogueService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultCatalogueService implements CatalogueService {
    private final CatalogueRepository repo;
    private final GoodsRepository goodsRepo;
    private final GoodsMapper mapper;

    @Override
    public GoodsPageDto getCatalogueGoods(Pageable pageable) {
        Page<CatalogueProduct> catalogue = repo.findAllAndOrderByOrder(pageable);
        Page<GoodsDto> goods = catalogue.map(
                catalogueProduct -> mapper.read(catalogueProduct.getProduct()));
        return GoodsPageDto.of(goods);
    }

    @Override
    public void addGoodsToCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsAlreadyExistException {
        if (!goodsRepo.existsById(goodsId))
            throw new GoodsNotFoundException("Goods with id %d does not exist.".formatted(goodsId));
        if (repo.existsById(goodsId))
            throw new CatalogueGoodsAlreadyExistException("Goods with id %d is already contains in the catalogue.".formatted(goodsId));
        repo.save(CatalogueProduct.builder()
                .goodsId(goodsId)
                .build());
    }

    @Override
    public void removeGoodsFromCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsAlreadyExistException {
        if (!goodsRepo.existsById(goodsId))
            throw new GoodsNotFoundException("Goods with id %d does not exist.".formatted(goodsId));
        Optional<CatalogueProduct> mayBeCatalogueGoods = repo.findById(goodsId);
        if (mayBeCatalogueGoods.isEmpty())
            throw new CatalogueGoodsAlreadyExistException("Goods with id %d is does not contains in the catalogue.".formatted(goodsId));
        repo.delete(mayBeCatalogueGoods.get());
    }

    @Override
    public void updateGoodsInCatalogue(UpdateCatalogueGoodsPayload payload) throws GoodsNotFoundException, CatalogueGoodsAlreadyExistException {
        Integer goodsId = payload.goodsId();
        if (!goodsRepo.existsById(goodsId))
            throw new GoodsNotFoundException("Goods with id %d does not exist.".formatted(goodsId));
        CatalogueProduct catalogueProduct = repo.findById(payload.goodsId()).orElseThrow(
                () -> new CatalogueGoodsAlreadyExistException("Goods with id %d does not contains in the catalogue.".formatted(goodsId)));
        catalogueProduct.setOrder(payload.order());
        repo.save(catalogueProduct);
    }
}
