package re1kur.catalogueservice.controller;

import dto.GoodsPageDto;
import filter.GoodsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.service.GoodsService;

@RestController
@RequestMapping("api/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService service;

    @GetMapping("get")
    public ResponseEntity<GoodsDto> getGoodsById(@RequestParam Integer id)  throws GoodsNotFoundException {
        return service.getById(id);
    }

    @GetMapping("list")
    public ResponseEntity<GoodsPageDto> getGoodsList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size,
            GoodsFilter filter) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getPage(pageable, filter);
    }

    @PostMapping("create")
    public void deleteGoods(@RequestBody GoodsPayload payload) throws CategoryNotFoundException, GoodsExistingException {
        service.create(payload);
    }

    @DeleteMapping("delete")
    public void deleteGoods(@RequestParam Integer id) throws GoodsNotFoundException {
        service.deleteById(id);
    }

    @PutMapping("update")
    public void updateGoods(@RequestBody GoodsUpdatePayload payload) throws CategoryNotFoundException {
        service.update(payload);
    }

}
