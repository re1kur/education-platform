package re1kur.catalogueservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.catalogueservice.dto.GoodsDto;
import re1kur.catalogueservice.dto.GoodsPayload;
import re1kur.catalogueservice.dto.GoodsUpdatePayload;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsExistingException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;

import java.util.List;

public interface GoodsService {
    ResponseEntity<GoodsDto> getById(Integer id) throws GoodsNotFoundException;

    void create(GoodsPayload payload) throws CategoryNotFoundException, GoodsExistingException;

    void deleteById(Integer id) throws GoodsNotFoundException;

    void update(GoodsUpdatePayload payload) throws CategoryNotFoundException;

    ResponseEntity<List<GoodsDto>> getList();
}
