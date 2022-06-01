package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Order;
import st43189.entity.UserProductOrderKey;

public interface OrderAtProductRepository extends JpaRepository<Order, UserProductOrderKey> {
}
