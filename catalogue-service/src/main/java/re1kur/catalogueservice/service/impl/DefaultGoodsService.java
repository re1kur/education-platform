package re1kur.catalogueservice.service.impl;

import dto.GoodsPageDto;
import filter.GoodsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsAlreadyExistException;
import re1kur.catalogueservice.exception.GoodsNotFoundException;
import re1kur.catalogueservice.mapper.GoodsMapper;
import re1kur.catalogueservice.repository.GoodsRepository;
import re1kur.catalogueservice.service.GoodsService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGoodsService implements GoodsService {
    private final GoodsRepository repo;
    private final GoodsMapper mapper;

    @Override
    public GoodsDto get(Integer id) throws GoodsNotFoundException {
        Goods goods = repo.findById(id)
                .orElseThrow(() -> new GoodsNotFoundException("Goods with id %d does not exits".formatted(id)));
        return mapper.read(goods);
    }

    @Override
    public void create(GoodsPayload payload) throws CategoryNotFoundException, GoodsAlreadyExistException {
        if (repo.existsByTitle(payload.title()))
            throw new GoodsAlreadyExistException("Goods with title '%s' already exists.".formatted(payload.title()));
        Goods goods = mapper.write(payload);
        repo.save(goods);
    }

    @Override
    public void delete(Integer id) throws GoodsNotFoundException {
        if (!repo.existsById(id)) throw new GoodsNotFoundException("Goods with id %d does not exits".formatted(id));
        repo.deleteById(id);
    }

    @Override
    public void update(GoodsUpdatePayload payload) throws CategoryNotFoundException {
        Goods goods = mapper.update(payload);
        repo.save(goods);
    }

    @Override
    public List<GoodsDto> getList() {
        return repo.findAll().stream().map(mapper::read).toList();
    }

    @Override
    public GoodsPageDto getPage(Pageable pageable, GoodsFilter filter) {
        Integer categoryId = filter.categoryId();
        BigDecimal price = filter.price();
        String title = filter.title();
        return GoodsPageDto.of(repo.findAllByFilter(pageable, categoryId, price, title).map(mapper::read));
    }
}
