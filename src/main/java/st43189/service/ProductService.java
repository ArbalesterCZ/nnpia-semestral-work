package st43189.service;

import org.springframework.stereotype.Service;
import st43189.entity.Category;
import st43189.entity.Product;
import st43189.repository.CategoryRepository;
import st43189.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product find(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id [" + id + "] not found."));
    }

    public Product createOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public Product delete(long id) {
        Optional<Product> found = productRepository.findById(id);
        found.ifPresent(productRepository::delete);
        return found.orElseGet(found::get);
    }

    public Category findCategory(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id [" + id + "] not found."));
    }
}
