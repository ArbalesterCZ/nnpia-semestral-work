package st43189.upce.cz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.upce.cz.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
