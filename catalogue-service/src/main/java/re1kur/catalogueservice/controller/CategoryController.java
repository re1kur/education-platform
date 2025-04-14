package re1kur.catalogueservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.CategoryDto;
import payload.CategoryPayload;
import payload.CategoryUpdatePayload;
import re1kur.catalogueservice.exception.CategoryAlreadyExistsException;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping("get")
    public ResponseEntity<CategoryDto> getCategory(@RequestParam Integer id) throws CategoryNotFoundException {
        return service.get(id);
    }

    @GetMapping("list")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return service.getList();
    }

    @PostMapping("create")
    public void createCategory(@RequestBody CategoryPayload payload) throws CategoryAlreadyExistsException {
        service.create(payload);
    }

    @DeleteMapping("delete")
    public void deleteCategory(@RequestParam Integer id) throws CategoryNotFoundException {
        service.delete(id);
    }

    @PutMapping("update")
    public void updateCategory(@RequestBody CategoryUpdatePayload payload) throws CategoryNotFoundException {
        service.update(payload);
    }
}
