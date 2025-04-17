package re1kur.catalogueservice.service;

import dto.GoodsPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import payload.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.exception.CatalogueGoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;


public interface CatalogueService {
    ResponseEntity<GoodsPageDto> getCatalogueGoods(Pageable pageable);

    void addGoodsToCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsExistingException;

    void removeGoodsFromCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsExistingException;

    void updateGoodsInCatalogue(UpdateCatalogueGoodsPayload payload) throws GoodsNotFoundException, CatalogueGoodsExistingException;
}
