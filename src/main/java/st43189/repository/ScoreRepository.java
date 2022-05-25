package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Product;
import st43189.entity.Score;
import st43189.entity.User;
import st43189.entity.UserProductKey;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, UserProductKey> {
    Optional<Score> findByUserAndProduct(User user, Product product);
    Optional<Score> findByUserIdAndProductId(long userId, long productId);

    List<Score> findAllByUserId(long id);
    List<Score> findAllByProductId(long id);
}
