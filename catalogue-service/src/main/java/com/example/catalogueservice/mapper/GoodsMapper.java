package com.example.catalogueservice.mapper;

import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.exception.CategoryNotFoundException;
import com.example.dto.GoodsDto;
import com.example.payload.GoodsPayload;
import com.example.payload.GoodsUpdatePayload;

public interface GoodsMapper {
    GoodsDto read(Product product);

    Product write(GoodsPayload payload) throws CategoryNotFoundException;

    Product update(GoodsUpdatePayload payload) throws CategoryNotFoundException;
}
