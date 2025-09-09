//package com.example.catalogueservice.mapper.impl;
//
//import com.example.catalogueservice.entity.Product;
//import com.example.dto.GoodsDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import com.example.catalogueservice.entity.Category;
//import com.example.catalogueservice.exception.CategoryNotFoundException;
//import com.example.catalogueservice.mapper.GoodsMapper;
//import com.example.catalogueservice.repository.CategoryRepository;
//
//@Component
//@RequiredArgsConstructor
//public class DefaultGoodsMapper implements GoodsMapper {
//    private final CategoryRepository repo;
//
//
//    @Override
//    public GoodsDto read(Product product) {
//        return new GoodsDto(
//                product.getId(),
//                product.getTitle(),
//                product.getCategory().getId(),
//                product.getDescription(),
//                product.getPrice(),
//                product.getInStock(),
//                product.getImageUrl());
//    }
//
//    @Override
//    public Product write(GoodsPayload payload) throws CategoryNotFoundException {
//        Category category = repo
//                .findById(payload.categoryId())
//                .orElseThrow(() -> new CategoryNotFoundException("Category with id %d does not exist.".formatted(payload.categoryId())));
//
//        return Product.builder()
//                .title(payload.title())
//                .category(category)
//                .price(payload.price())
//                .description(payload.description())
//                .build();
//    }
//
//    @Override
//    public Product update(GoodsUpdatePayload payload) throws CategoryNotFoundException {
//        Category category = repo
//                .findById(payload.categoryId())
//                .orElseThrow(() -> new CategoryNotFoundException("Category with id %d does not exist.".formatted(payload.categoryId())));
//
//        return Product.builder()
//                .id(payload.id())
//                .category(category)
//                .title(payload.title())
//                .description(payload.description())
//                .price(payload.price())
////                .imageUrl(payload.imageUrl())
//                .inStock(payload.inStock())
//                .build();
//    }
//}
