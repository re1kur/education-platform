package re1kur.catalogueservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.catalogueservice.dto.CategoryDto;
import re1kur.catalogueservice.dto.CategoryPayload;
import re1kur.catalogueservice.dto.CategoryUpdatePayload;
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
