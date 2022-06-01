package st43189.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import st43189.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products p WHERE p.enabled = true", nativeQuery = true)
    List<Product> findAllByEnabledTrue(PageRequest pageRequest);

    int countAllByEnabledTrue();

    Product findByNameContains(String name);

    Product findByPriceBetween(BigDecimal min, BigDecimal max);
}
