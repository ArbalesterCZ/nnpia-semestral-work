package st43189.upce.cz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.upce.cz.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}