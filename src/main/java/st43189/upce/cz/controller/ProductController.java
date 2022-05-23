package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.*;
import st43189.upce.cz.dto.ProductDto;
import st43189.upce.cz.entity.Product;
import st43189.upce.cz.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product create(@RequestBody ProductDto dto) {
        return productService.createOrUpdate((fromDto(dto)));
    }

    @GetMapping
    public List<Product> readAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product read(@PathVariable long id) {
        return productService.find(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable long id, @RequestBody ProductDto dto) {
        Product product = fromDto(dto);
        product.setId(id);
        return productService.createOrUpdate(product);
    }

    @DeleteMapping("/{id}")
    public Product delete(@PathVariable long id) {
        return productService.delete(id);
    }

    private Product fromDto(ProductDto dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(productService.findCategory(dto.getCategoryId()));

        return product;
    }
}
