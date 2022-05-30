package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Product;
import st43189.entity.ProductInCart;
import st43189.entity.User;
import st43189.entity.UserProductKey;

import java.util.List;
import java.util.Optional;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, UserProductKey> {
    Optional<ProductInCart> findByUserAndProduct(User user, Product product);
    Optional<ProductInCart> findByUserIdAndProductId(long userId, long productId);

    List<ProductInCart> findAllByUserIdOrderById(long id);
    List<ProductInCart> findAllByProductId(long id);
}
