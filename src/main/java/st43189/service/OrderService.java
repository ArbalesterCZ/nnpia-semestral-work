package st43189.service;

import org.springframework.stereotype.Service;
import st43189.dto.OrderDto;
import st43189.entity.*;
import st43189.repository.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final ProductAtOrderRepository productAtOrderRepository;
    private final UserRepository userRepository;
    private final ProductInCartRepository productInCartRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductAtOrderRepository orderRepository, UserRepository userRepository, ProductInCartRepository productInCartRepository, OrderRepository orderRepository1) {
        this.productAtOrderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productInCartRepository = productInCartRepository;
        this.orderRepository = orderRepository1;
    }

    public List<ProductAtOrder> doOrder(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            List<ProductInCart> cart = productInCartRepository.findAllByUserIdOrderById(user.getId());
            List<ProductAtOrder> productAtOrders = new LinkedList<>();

            Order newOrder = new Order();
            orderRepository.save(newOrder);
            for (ProductInCart item : cart) {

                ProductAtOrder productAtOrder = new ProductAtOrder();
                productAtOrder.setAmount(item.getAmount());
                productAtOrder.setUser(user);
                productAtOrder.setProduct(item.getProduct());
                productAtOrder.setOrder(newOrder);
                productAtOrder.setId(new UserProductOrderKey(user.getId(), item.getProduct().getId(), newOrder.getId()));

                productAtOrders.add(productAtOrder);

                productAtOrderRepository.save(productAtOrder);
                productInCartRepository.delete(item);
            }
            return productAtOrders;
        }
        return new LinkedList<>();
    }

    public List<ProductAtOrder> getAllOrderOf(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent())
            return productAtOrderRepository.findAllByUser(foundUser.get());

        return new LinkedList<>();
    }

    public List<ProductInCart> getAllProductAtOrder(String email, long orderId) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            List<ProductAtOrder> pao = productAtOrderRepository.findAllByUserAndOrder(foundUser.get(), orderRepository.getOne(orderId));

            List<ProductInCart> dtoList = new LinkedList<>();
            for (ProductAtOrder p : pao) {
                ProductInCart productInCart = new ProductInCart();
                productInCart.setId(new UserProductKey(p.getId().getUserId(), p.getId().getProductId()));
                productInCart.setAmount(p.getAmount());
                productInCart.setProduct(p.getProduct());
                productInCart.setUser(p.getUser());
                dtoList.add(productInCart);
            }

            return dtoList;
        }
        return null;
    }
}
