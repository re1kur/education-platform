package com.example.catalogueservice.mapper.impl;

import com.example.catalogueservice.entity.Category;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.mapper.ProductMapper;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.payload.ProductPayload;
import com.example.payload.ProductUpdatePayload;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDto read(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getForSale(),
                product.getSingle(),
                product.getPreviewDescription(),
                product.getDescription(),
                product.getCategory().getId(),
                product.getFileIds());
    }

    @Override
    public Product create(ProductPayload payload, Category category, List<UUID> fileIds) {
        return Product.builder()
                .price(payload.price())
                .title(payload.title())
                .description(payload.description())
                .previewDescription(payload.previewDescription())
                .single(payload.single())
                .category(category)
                .fileIds(fileIds)
                .build();
    }

    @Override
    public Product update(Product product, ProductUpdatePayload payload) {
        return null;
    }

    @Override
    public PageDto<ProductDto> readPage(Page<Product> page) {
        return new PageDto<>(
                page.stream().map(this::read).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious());
    }
}
