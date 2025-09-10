package com.example.catalogueservice.mapper;

import com.example.catalogueservice.entity.Category;
import com.example.catalogueservice.entity.Product;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.payload.ProductPayload;
import com.example.payload.ProductUpdatePayload;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductMapper {
    ProductDto read(Product product);

    Product create(ProductPayload payload, Category category, List<UUID> fileIds);

    Product update(Product product, ProductUpdatePayload payload);

    PageDto<ProductDto> readPage(Page<Product> page);
}
