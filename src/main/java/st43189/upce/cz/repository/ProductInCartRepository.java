package st43189.upce.cz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.upce.cz.entity.Product;
import st43189.upce.cz.entity.ProductInCart;
import st43189.upce.cz.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {
    Optional<ProductInCart> findByUserAndProduct(User user, Product product);
    Optional<ProductInCart> findByUserIdAndProductId(long userId, long productId);

    List<ProductInCart> findAllByUserId(long id);
    List<ProductInCart> findAllByProductId(long id);
}
