package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Order;
import st43189.entity.ProductAtOrder;
import st43189.entity.User;

import java.util.List;

public interface ProductAtOrderRepository extends JpaRepository<ProductAtOrder, Long> {

    List<ProductAtOrder> findAllByUser(User user);

    List<ProductAtOrder> findAllByUserAndOrder(User user, Order order);
}
