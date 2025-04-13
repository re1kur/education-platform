package re1kur.catalogueservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re1kur.catalogueservice.dto.GoodsDto;
import re1kur.catalogueservice.dto.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.exception.CatalogueGoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.service.CatalogueService;

import java.util.Set;

@RestController
@RequestMapping("api/catalogue")
@RequiredArgsConstructor
public class CatalogueController {
    private final CatalogueService service;

    @GetMapping("get")
    public ResponseEntity<Set<GoodsDto>> getCatalogue() {
        return service.getCatalogueGoods();
    }

    @PostMapping("add")
    public void addGoodsToCatalogue(@RequestParam Integer id) throws CatalogueGoodsExistingException, GoodsNotFoundException {
        service.addGoodsToCatalogue(id);
    }

    @DeleteMapping("remove")
    public void removeGoodsFromCatalogue(@RequestParam Integer id) throws CatalogueGoodsExistingException, GoodsNotFoundException {
        service.removeGoodsFromCatalogue(id);
    }

    @PutMapping("update")
    public void updateGoodsInCatalogue(@RequestBody UpdateCatalogueGoodsPayload payload) throws CatalogueGoodsExistingException, GoodsNotFoundException {
        service.updateGoodsInCatalogue(payload);
    }
}
