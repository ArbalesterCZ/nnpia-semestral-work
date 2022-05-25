package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByNameContains(String name);
    Product findByPriceBetween(BigDecimal min, BigDecimal max);
}