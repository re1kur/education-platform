package re1kur.catalogueservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dto.CategoryDto;
import payload.CategoryPayload;
import payload.CategoryUpdatePayload;
import re1kur.catalogueservice.entity.Category;
import re1kur.catalogueservice.exception.CategoryAlreadyExistException;
import re1kur.catalogueservice.exception.CategoryNotFoundException;
import re1kur.catalogueservice.mapper.CategoryMapper;
import re1kur.catalogueservice.repository.CategoryRepository;
import re1kur.catalogueservice.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto get(Integer id) throws CategoryNotFoundException {
        Category category = repo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id %d does not exist.".formatted(id)));
        return mapper.read(category);
    }

    @Override    
    public void create(CategoryPayload payload) throws CategoryAlreadyExistException {
        if (repo.existsByTitle(payload.title()))
            throw new CategoryAlreadyExistException("Category with title %s is already exists.".formatted(payload.title()));
        Category category = mapper.write(payload);
        repo.save(category);
    }

    @Override
    public void delete(Integer id) throws CategoryNotFoundException {
        Optional<Category> mayBeCategory = repo.findById(id);
        if (mayBeCategory.isEmpty())
            throw new CategoryNotFoundException("Category with id %d does not exist.".formatted(id));
        repo.delete(mayBeCategory.get());
    }

    @Override
    public void update(CategoryUpdatePayload payload) throws CategoryNotFoundException {
        Optional<Category> mayBeCategory = repo.findById(payload.id());
        if (mayBeCategory.isEmpty())
            throw new CategoryNotFoundException("Category with id %d does not exist.".formatted(payload.id()));
        Category category = mayBeCategory.get();
        category.setTitle(payload.title());
        category.setDescription(payload.description());
    }

    @Override
    public List<CategoryDto> getList() {
        List<Category> list = (List<Category>) repo.findAll();
        return list.stream().map(mapper::read).toList();
    }
}
