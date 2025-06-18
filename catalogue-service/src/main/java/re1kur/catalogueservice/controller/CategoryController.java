package re1kur.catalogueservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.CategoryDto;
import payload.CategoryPayload;
import payload.CategoryUpdatePayload;
import re1kur.catalogueservice.exception.CategoryAlreadyExistException;
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
        CategoryDto body = service.get(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(body);
    }

    @GetMapping("list")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> body = service.getList();
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("create")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryPayload payload) throws CategoryAlreadyExistException {
        service.create(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category %s has been created.".formatted(payload.title()));
    }

    @DeleteMapping("delete")
    public void deleteCategory(@RequestParam Integer id) throws CategoryNotFoundException {
        service.delete(id);
    }

    @PutMapping("update")
    public void updateCategory(@RequestBody @Valid CategoryUpdatePayload payload) throws CategoryNotFoundException {
        service.update(payload);
    }
}
