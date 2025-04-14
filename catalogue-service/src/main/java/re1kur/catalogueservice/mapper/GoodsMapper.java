package re1kur.catalogueservice.mapper;

import dto.GoodsDto;
import payload.GoodsPayload;
import payload.GoodsUpdatePayload;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;

public interface GoodsMapper {
    GoodsDto read(Goods goods);

    Goods write(GoodsPayload payload) throws CategoryNotFoundException;

    Goods update(GoodsUpdatePayload payload) throws CategoryNotFoundException;
}
