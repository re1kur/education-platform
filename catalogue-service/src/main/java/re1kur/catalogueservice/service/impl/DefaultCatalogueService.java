package re1kur.catalogueservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import re1kur.catalogueservice.dto.GoodsDto;
import re1kur.catalogueservice.dto.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.entity.CatalogueGoods;
import re1kur.catalogueservice.exception.CatalogueGoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.mapper.GoodsMapper;
import re1kur.catalogueservice.repository.CatalogueRepository;
import re1kur.catalogueservice.repository.GoodsRepository;
import re1kur.catalogueservice.service.CatalogueService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class DefaultCatalogueService implements CatalogueService {
    private final CatalogueRepository repo;
    private final GoodsRepository goodsRepo;
    private final GoodsMapper mapper;

    @Override
    public ResponseEntity<Set<GoodsDto>> getCatalogueGoods() {
        List<CatalogueGoods> catalogue = repo.findAllAndOrderByOrder();
        Set<GoodsDto> goods = catalogue.stream().map(
                catalogueGoods -> mapper.read(catalogueGoods.getGoods())).collect(toSet());
        return ResponseEntity.status(HttpStatus.OK).body(goods);
    }

    @Override
    public void addGoodsToCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsExistingException {
        if (!goodsRepo.existsById(goodsId))
            throw new GoodsNotFoundException("Goods with id %d does not exist.".formatted(goodsId));
        if (repo.existsById(goodsId))
            throw new CatalogueGoodsExistingException("Goods with id %d is already contains in the catalogue.".formatted(goodsId));
        repo.save(CatalogueGoods.builder()
                .userId(goodsId)
                .build());
    }

    @Override
    public void removeGoodsFromCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsExistingException {
        if (!goodsRepo.existsById(goodsId))
            throw new GoodsNotFoundException("Goods with id %d does not exist.".formatted(goodsId));
        Optional<CatalogueGoods> mayBeCatalogueGoods = repo.findById(goodsId);
        if (mayBeCatalogueGoods.isEmpty())
            throw new CatalogueGoodsExistingException("Goods with id %d is does not contains in the catalogue.".formatted(goodsId));
        repo.delete(mayBeCatalogueGoods.get());
    }

    @Override
    public void updateGoodsInCatalogue(UpdateCatalogueGoodsPayload payload) throws GoodsNotFoundException, CatalogueGoodsExistingException {
        Integer goodsId = payload.goodsId();
        if (!goodsRepo.existsById(goodsId))
            throw new GoodsNotFoundException("Goods with id %d does not exist.".formatted(goodsId));
        CatalogueGoods catalogueGoods = repo.findById(payload.goodsId()).orElseThrow(
                () -> new CatalogueGoodsExistingException("Goods with id %d does not contains in the catalogue.".formatted(goodsId)));
        catalogueGoods.setOrder(payload.order());
        repo.save(catalogueGoods);

    }
}
