package com.example.catalogueservice.controller;

import com.example.dto.GoodsDto;
import com.example.dto.GoodsPageDto;
import com.example.filter.ProductsFilter;
import com.example.payload.GoodsPayload;
import com.example.payload.GoodsUpdatePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.catalogueservice.exception.CategoryNotFoundException;
import com.example.catalogueservice.exception.GoodsAlreadyExistException;
import com.example.catalogueservice.exception.GoodsNotFoundException;
import com.example.catalogueservice.service.GoodsService;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/goods")
@RequiredArgsConstructor
public class ProductsController {
    private final GoodsService service;

    @GetMapping("get")
    public ResponseEntity<GoodsDto> getGoodsById(@RequestParam Integer id)  throws GoodsNotFoundException {
        GoodsDto body = service.get(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(body);
    }

    @GetMapping("list")
    public ResponseEntity<GoodsPageDto> getGoodsList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) BigDecimal price) {

        ProductsFilter filter = new ProductsFilter(title, categoryId, price);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getPage(pageable, filter));
    }

    @PostMapping("create")
    public ResponseEntity<String> createGoods(@RequestBody @Valid GoodsPayload payload) throws CategoryNotFoundException, GoodsAlreadyExistException {
        service.create(payload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("delete")
    public void deleteGoods(@RequestParam Integer id) throws GoodsNotFoundException {
        service.delete(id);
    }

    @PutMapping("update")
    public void updateGoods(@RequestBody @Valid GoodsUpdatePayload payload) throws CategoryNotFoundException {
        service.update(payload);
    }
}
