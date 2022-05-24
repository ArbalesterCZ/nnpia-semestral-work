package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ProductDto;
import st43189.upce.cz.entity.Product;
import st43189.upce.cz.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDto create(@RequestBody ProductDto dto) {
        return toDto(productService.createOrUpdate((fromDto(dto))));
    }

    @GetMapping
    public List<ProductDto> readAll() {
        return productService
                .getAll()
                .stream()
                .map(ProductController::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto read(@PathVariable long id) {
        return toDto(productService.find(id));
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable long id, @RequestBody ProductDto dto) {
        Product product = fromDto(dto);
        product.setId(id);
        return toDto(productService.createOrUpdate(product));
    }

    @DeleteMapping("/{id}")
    public ProductDto delete(@PathVariable long id) {
        return toDto(productService.delete(id));
    }

    private Product fromDto(ProductDto dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(productService.findCategory(dto.getCategoryId()));

        return product;
    }

    private static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategoryId(product.getCategory().getId());

        return dto;
    }
}
