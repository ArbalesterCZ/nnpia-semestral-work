package st43189.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import st43189.entity.Category;
import st43189.entity.Product;
import st43189.repository.CategoryRepository;
import st43189.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
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

    public Page<Product> getAll(int pageNumber, int pageSize, String sortBy) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortBy));
    }

    public long getCount() {
        return productRepository.count();
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
