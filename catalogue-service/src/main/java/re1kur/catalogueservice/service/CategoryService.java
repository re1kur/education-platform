package re1kur.catalogueservice.service;

import dto.CategoryDto;
import payload.CategoryPayload;
import payload.CategoryUpdatePayload;
import re1kur.catalogueservice.exception.CategoryAlreadyExistException;
import re1kur.catalogueservice.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    CategoryDto get(Integer id) throws CategoryNotFoundException;

    void create(CategoryPayload payload) throws CategoryAlreadyExistException;

    void delete(Integer id) throws CategoryNotFoundException;

    void update(CategoryUpdatePayload payload) throws CategoryNotFoundException;

    List<CategoryDto> getList();
}
