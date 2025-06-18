package re1kur.catalogueservice.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.mapper.GoodsMapper;
import re1kur.catalogueservice.repository.CategoryRepository;

@Component
@RequiredArgsConstructor
public class DefaultGoodsMapper implements GoodsMapper {
    private final CategoryRepository repo;


    @Override
    public GoodsDto read(Goods goods) {
        return new GoodsDto(
                goods.getId(),
                goods.getTitle(),
                goods.getCategory().getId(),
                goods.getDescription(),
                goods.getPrice(),
                goods.getInStock(),
                goods.getImageUrl());
    }

    @Override
    public Goods write(GoodsPayload payload) throws CategoryNotFoundException {
        Category category = repo
                .findById(payload.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id %d does not exist.".formatted(payload.categoryId())));

        return Goods.builder()
                .title(payload.title())
                .category(category)
                .price(payload.price())
                .description(payload.description())
                .build();
    }

    @Override
    public Goods update(GoodsUpdatePayload payload) throws CategoryNotFoundException {
        Category category = repo
                .findById(payload.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id %d does not exist.".formatted(payload.categoryId())));

        return Goods.builder()
                .id(payload.id())
                .category(category)
                .title(payload.title())
                .description(payload.description())
                .price(payload.price())
//                .imageUrl(payload.imageUrl())
                .inStock(payload.inStock())
                .build();
    }
}
