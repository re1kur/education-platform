package re1kur.catalogueservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.catalogueservice.dto.GoodsDto;
import re1kur.catalogueservice.dto.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.exception.CatalogueGoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;

import java.util.Set;

public interface CatalogueService {
    ResponseEntity<Set<GoodsDto>> getCatalogueGoods();

    void addGoodsToCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsExistingException;

    void removeGoodsFromCatalogue(Integer goodsId) throws GoodsNotFoundException, CatalogueGoodsExistingException;

    void updateGoodsInCatalogue(UpdateCatalogueGoodsPayload payload) throws GoodsNotFoundException, CatalogueGoodsExistingException;
}
