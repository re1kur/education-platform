package re1kur.catalogueservice.service;

import org.springframework.http.ResponseEntity;
import dto.CategoryDto;
import payload.CategoryPayload;
import payload.CategoryUpdatePayload;
import re1kur.catalogueservice.exception.CategoryAlreadyExistsException;
import re1kur.catalogueservice.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    ResponseEntity<CategoryDto> get(Integer id) throws CategoryNotFoundException;

    void create(CategoryPayload payload) throws CategoryAlreadyExistsException;

    void delete(Integer id) throws CategoryNotFoundException;

    void update(CategoryUpdatePayload payload) throws CategoryNotFoundException;

    ResponseEntity<List<CategoryDto>> getList();
}
