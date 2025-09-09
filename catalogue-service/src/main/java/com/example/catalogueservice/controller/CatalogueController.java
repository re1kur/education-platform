package com.example.catalogueservice.controller;

import dto.GoodsPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.UpdateCatalogueGoodsPayload;
import com.example.catalogueservice.exception.CatalogueGoodsAlreadyExistException;
import com.example.catalogueservice.exception.GoodsNotFoundException;
import com.example.catalogueservice.service.CatalogueService;

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
        GoodsPageDto pageDto = service.getCatalogueGoods(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pageDto);
    }

    @PostMapping("add")
    public void addGoodsToCatalogue(@RequestParam Integer id) throws CatalogueGoodsAlreadyExistException, GoodsNotFoundException {
        service.addGoodsToCatalogue(id);
    }

    @DeleteMapping("remove")
    public void removeGoodsFromCatalogue(@RequestParam Integer id) throws CatalogueGoodsAlreadyExistException, GoodsNotFoundException {
        service.removeGoodsFromCatalogue(id);
    }

    @PutMapping("update")
    public void updateGoodsInCatalogue(@RequestBody UpdateCatalogueGoodsPayload payload) throws CatalogueGoodsAlreadyExistException, GoodsNotFoundException {
        service.updateGoodsInCatalogue(payload);
    }
}
