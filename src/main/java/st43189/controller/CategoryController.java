package st43189.controller;

import org.springframework.web.bind.annotation.*;
import st43189.dto.CategoryDto;
import st43189.entity.Category;
import st43189.service.CategoryService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto dto) {
        return toDto(categoryService.createOrUpdate(fromDto(dto)));
    }

    @GetMapping
    public List<CategoryDto> readAll() {
        List<CategoryDto> dtoList = new LinkedList<>();
        categoryService.getAll().forEach(category -> dtoList.add(toDto(category)));

        return dtoList;
    }

    @GetMapping("/{id}")
    public CategoryDto read(@PathVariable long id) {
        return toDto(categoryService.find(id));
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable long id, @RequestBody CategoryDto dto) {
        Category category = fromDto(dto);
        category.setId(id);
        return toDto(categoryService.createOrUpdate(category));
    }

    @DeleteMapping("/{id}")
    public CategoryDto delete(@PathVariable long id) {
        return toDto(categoryService.delete(id));
    }

    private Category fromDto(CategoryDto dto) {
        Category category = new Category();

        category.setName(dto.getName());

        return category;
    }

    private CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }
}
