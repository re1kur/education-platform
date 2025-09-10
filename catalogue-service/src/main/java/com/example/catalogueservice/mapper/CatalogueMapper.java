package com.example.catalogueservice.mapper;

import com.example.catalogueservice.entity.CatalogueProduct;
import com.example.catalogueservice.entity.Product;
import com.example.dto.CatalogueProductDto;
import com.example.payload.CatalogueProductPayload;
import com.example.payload.UpdateCataloguePayloadPayload;

public interface CatalogueMapper {
    CatalogueProduct create(CatalogueProductPayload payload, Product found);

    CatalogueProductDto read(CatalogueProduct saved);

    CatalogueProduct update(CatalogueProduct found, UpdateCataloguePayloadPayload payload);
}
