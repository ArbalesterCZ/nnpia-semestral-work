package st43189.controller;

import org.springframework.web.bind.annotation.*;
import st43189.dto.ProductDto;
import st43189.entity.Product;
import st43189.service.ProductService;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
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
        List<ProductDto> dtoList = new LinkedList<>();
        productService.getAll().forEach(product -> dtoList.add(toDto(product)));

        return dtoList;
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

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategoryId(product.getCategory().getId());

        return dto;
    }
}
