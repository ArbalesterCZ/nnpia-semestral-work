package st43189.upce.cz.service;

import org.springframework.stereotype.Service;
import st43189.upce.cz.entity.Category;
import st43189.upce.cz.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category find(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id [" + id + "] not found."));
    }

    public Category createOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    public Category delete(long id) {
        Optional<Category> found = categoryRepository.findById(id);
        found.ifPresent(categoryRepository::delete);
        return found.orElseGet(found::get);
    }
}
