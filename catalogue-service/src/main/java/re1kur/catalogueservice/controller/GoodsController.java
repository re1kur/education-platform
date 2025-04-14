package re1kur.catalogueservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.service.GoodsService;

import java.util.List;

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
    public ResponseEntity<List<GoodsDto>> getGoodsList() {
        return service.getList();
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
