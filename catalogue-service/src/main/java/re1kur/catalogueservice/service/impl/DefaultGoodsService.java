package re1kur.catalogueservice.service.impl;

import dto.GoodsPageDto;
import filter.GoodsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.exception.GoodsExistingException;
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
    public ResponseEntity<GoodsDto> getById(Integer id) throws GoodsNotFoundException {
        Goods goods = repo.findById(id)
                .orElseThrow(() -> new GoodsNotFoundException("Goods with id %d does not exits".formatted(id)));
        GoodsDto serializable = mapper.read(goods);
        return ResponseEntity.status(HttpStatus.FOUND).body(serializable);
    }

    @Override
    public void create(GoodsPayload payload) throws CategoryNotFoundException, GoodsExistingException {
        if (repo.existsByTitle(payload.title()))
            throw new GoodsExistingException("Goods with title '%s' already exists.".formatted(payload.title()));
        Goods goods = mapper.write(payload);
        repo.save(goods);
    }

    @Override
    public void deleteById(Integer id) throws GoodsNotFoundException {
        if (!repo.existsById(id)) throw new GoodsNotFoundException("Goods with id %d does not exits".formatted(id));
        repo.deleteById(id);
    }

    @Override
    public void update(GoodsUpdatePayload payload) throws CategoryNotFoundException {
        Goods goods = mapper.update(payload);
        repo.save(goods);
    }

    @Override
    public ResponseEntity<List<GoodsDto>> getList() {
        List<GoodsDto> list = repo.findAll().stream().map(mapper::read).toList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Override
    public ResponseEntity<GoodsPageDto> getPage(Pageable pageable, GoodsFilter filter) {
        Integer categoryId = filter.categoryId();
        BigDecimal price = filter.price();
        String title = filter.title();
        GoodsPageDto page = GoodsPageDto.of(repo.findAll(pageable, categoryId, price, title).map(mapper::read));
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
