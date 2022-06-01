package st43189.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<Product> getAll(int pageNumber, int pageSize, String sortBy) {
        return productRepository.findAllByEnabledTrue(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortBy));
    }

    public long getCount() {
        return productRepository.countAllByEnabledTrue();
    }

    public Product find(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id [" + id + "] not found."));
    }

    public Product createOrUpdate(Product product) {
        if (product.getId() != 0 && product.getImage() == null)
            product.setImage(productRepository.getOne(product.getId()).getImage());

        return productRepository.save(product);
    }

    public Product delete(long id) {
        Optional<Product> found = productRepository.findById(id);
        found.ifPresent(product -> product.setEnabled(false));
        found.ifPresent(productRepository::save);
        return found.orElseGet(found::get);
    }

    public Category findCategory(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id [" + id + "] not found."));
    }
}
