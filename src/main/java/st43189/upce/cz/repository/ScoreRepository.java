package st43189.upce.cz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.upce.cz.entity.Product;
import st43189.upce.cz.entity.Score;
import st43189.upce.cz.entity.User;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByUserAndProduct(User user, Product product);
    Optional<Score> findByUserIdAndProductId(long userId, long productId);

    List<Score> findAllByUserId(long id);
    List<Score> findAllByProductId(long id);
}
