package re1kur.catalogueservice.service;

import dto.GoodsPageDto;
import org.springframework.data.domain.Pageable;
import payload.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.exception.CatalogueGoodsAlreadyExistException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;


public interface CatalogueService {
    GoodsPageDto getCatalogueGoods(Pageable pageable);

    void addGoodsToCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsAlreadyExistException;

    void removeGoodsFromCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsAlreadyExistException;

    void updateGoodsInCatalogue(UpdateCatalogueGoodsPayload payload) throws GoodsNotFoundException, CatalogueGoodsAlreadyExistException;
}
