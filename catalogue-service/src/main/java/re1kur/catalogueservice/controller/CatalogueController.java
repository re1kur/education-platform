package re1kur.catalogueservice.controller;

import dto.GoodsPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.UpdateCatalogueGoodsPayload;
import re1kur.catalogueservice.exception.CatalogueGoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.service.CatalogueService;

@RestController
@RequestMapping("api/catalogue")
@RequiredArgsConstructor
public class CatalogueController {
    private final CatalogueService service;

    @GetMapping("get")
    public ResponseEntity<GoodsPageDto> getCatalogue(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getCatalogueGoods(pageable);
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
