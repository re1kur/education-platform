package com.example.catalogueservice.service;

import com.example.dto.GoodsDto;
import com.example.dto.GoodsPageDto;
import com.example.filter.ProductsFilter;
import com.example.payload.GoodsPayload;
import com.example.payload.GoodsUpdatePayload;
import org.springframework.data.domain.Pageable;
import com.example.catalogueservice.exception.CategoryNotFoundException;
import com.example.catalogueservice.exception.GoodsAlreadyExistException;
import com.example.catalogueservice.exception.GoodsNotFoundException;

import java.util.List;

public interface GoodsService {
    GoodsDto get(Integer id) throws GoodsNotFoundException;

    void create(GoodsPayload payload) throws CategoryNotFoundException, GoodsAlreadyExistException;

    void delete(Integer id) throws GoodsNotFoundException;

    void update(GoodsUpdatePayload payload) throws CategoryNotFoundException;

    List<GoodsDto> getList();

    GoodsPageDto getPage(Pageable pageable, ProductsFilter filter);
}
