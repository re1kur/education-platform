package com.example.catalogueservice.mapper.impl;

import com.example.catalogueservice.entity.CatalogueProduct;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.mapper.CatalogueMapper;
import com.example.dto.CatalogueProductDto;
import com.example.payload.CatalogueProductPayload;
import com.example.payload.UpdateCataloguePayloadPayload;
import org.springframework.stereotype.Component;

@Component
public class CatalogueMapperImpl implements CatalogueMapper {
    @Override
    public CatalogueProduct create(CatalogueProductPayload payload, Product product) {
        return CatalogueProduct.builder()
                .product(product)
                .priority(payload.priority())
                .build();
    }

    @Override
    public CatalogueProductDto read(CatalogueProduct saved) {
        return CatalogueProductDto.builder()
                .productId(saved.getProduct().getId())
                .priority(saved.getPriority())
                .build();
    }

    @Override
    public CatalogueProduct update(CatalogueProduct found, UpdateCataloguePayloadPayload payload) {
        found.setPriority(payload.priority());

        return found;
    }
}
