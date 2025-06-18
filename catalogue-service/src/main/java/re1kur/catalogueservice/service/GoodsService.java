package re1kur.catalogueservice.service;

import dto.GoodsPageDto;
import filter.GoodsFilter;
import org.springframework.data.domain.Pageable;
import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsAlreadyExistException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;

import java.util.List;

public interface GoodsService {
    GoodsDto get(Integer id) throws GoodsNotFoundException;

    void create(GoodsPayload payload) throws CategoryNotFoundException, GoodsAlreadyExistException;

    void delete(Integer id) throws GoodsNotFoundException;

    void update(GoodsUpdatePayload payload) throws CategoryNotFoundException;

    List<GoodsDto> getList();

    GoodsPageDto getPage(Pageable pageable, GoodsFilter filter);
}
