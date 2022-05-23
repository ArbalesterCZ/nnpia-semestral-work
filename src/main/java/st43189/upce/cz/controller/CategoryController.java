package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.CategoryDto;
import st43189.upce.cz.entity.Category;
import st43189.upce.cz.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/Category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category create(@RequestBody CategoryDto dto) {
        return categoryService.createOrUpdate(fromDto(dto));
    }

    @GetMapping
    public List<Category> readAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category read(@PathVariable long id) {
        return categoryService.find(id);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable long id, @RequestBody CategoryDto dto) {
        Category category = fromDto(dto);
        category.setId(id);
        return categoryService.createOrUpdate(category);
    }

    @DeleteMapping("/{id}")
    public Category delete(@PathVariable long id) {
        return categoryService.delete(id);
    }

    private Category fromDto(CategoryDto dto) {
        Category category = new Category();

        category.setName(dto.getName());

        return category;
    }
}
