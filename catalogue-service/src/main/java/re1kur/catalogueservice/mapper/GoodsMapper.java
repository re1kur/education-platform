package re1kur.catalogueservice.mapper;

import re1kur.catalogueservice.dto.GoodsDto;
import re1kur.catalogueservice.dto.GoodsPayload;
import re1kur.catalogueservice.dto.GoodsUpdatePayload;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.exception.CategoryNotFoundException;

public interface GoodsMapper {
    GoodsDto read(Goods goods);

    Goods write(GoodsPayload payload) throws CategoryNotFoundException;

    Goods update(GoodsUpdatePayload payload) throws CategoryNotFoundException;
}
