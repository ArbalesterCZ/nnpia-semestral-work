package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
