package com.example.catalogueservice.service.impl;

import com.example.catalogueservice.entity.Product;
import com.example.dto.GoodsDto;
import com.example.dto.GoodsPageDto;
import com.example.filter.ProductsFilter;
import com.example.payload.GoodsPayload;
import com.example.payload.GoodsUpdatePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.catalogueservice.exception.CategoryNotFoundException;
import com.example.catalogueservice.exception.GoodsAlreadyExistException;
import com.example.catalogueservice.exception.GoodsNotFoundException;
import com.example.catalogueservice.mapper.GoodsMapper;
import com.example.catalogueservice.repository.GoodsRepository;
import com.example.catalogueservice.service.GoodsService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGoodsService implements GoodsService {
    private final GoodsRepository repo;
    private final GoodsMapper mapper;

    @Override
    public GoodsDto get(Integer id) throws GoodsNotFoundException {
        Product product = repo.findById(id)
                .orElseThrow(() -> new GoodsNotFoundException("Goods with id %d does not exits".formatted(id)));
        return mapper.read(product);
    }

    @Override
    public void create(GoodsPayload payload) throws CategoryNotFoundException, GoodsAlreadyExistException {
        if (repo.existsByTitle(payload.title()))
            throw new GoodsAlreadyExistException("Goods with title '%s' already exists.".formatted(payload.title()));
        Product product = mapper.write(payload);
        repo.save(product);
    }

    @Override
    public void delete(Integer id) throws GoodsNotFoundException {
        if (!repo.existsById(id)) throw new GoodsNotFoundException("Goods with id %d does not exits".formatted(id));
        repo.deleteById(id);
    }

    @Override
    public void update(GoodsUpdatePayload payload) throws CategoryNotFoundException {
        Product product = mapper.update(payload);
        repo.save(product);
    }

    @Override
    public List<GoodsDto> getList() {
        return repo.findAll().stream().map(mapper::read).toList();
    }

    @Override
    public GoodsPageDto getPage(Pageable pageable, ProductsFilter filter) {
        Integer categoryId = filter.categoryId();
        BigDecimal price = filter.price();
        String title = filter.title();
        return GoodsPageDto.of(repo.findAllByFilter(pageable, categoryId, price, title).map(mapper::read));
    }
}
