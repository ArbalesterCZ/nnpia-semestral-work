package st43189.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st43189.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
