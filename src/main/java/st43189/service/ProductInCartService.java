package st43189.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import st43189.entity.Product;
import st43189.entity.ProductInCart;
import st43189.entity.User;
import st43189.repository.ProductInCartRepository;
import st43189.repository.ProductRepository;
import st43189.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductInCartService {

    private final ProductInCartRepository productInCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ProductInCartService(ProductInCartRepository productInCartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.productInCartRepository = productInCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<ProductInCart> getAll() {
        return productInCartRepository.findAll();
    }

    public List<ProductInCart> getAllOfUser(Authentication authentication) {
        return userRepository
                .findByEmail(authentication.getName())
                .map(user -> productInCartRepository.findAllByUserId(user.getId()))
                .orElseThrow(() -> new NoSuchElementException("User" + authentication.getName() + "not found"));
    }

    public ProductInCart find(long userId, long productId) {
        return productInCartRepository
                .findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NoSuchElementException("Product in cart with user id [" + userId + "] and product id [" + productId + "] not found."));
    }

    public ProductInCart createOrUpdate(ProductInCart productInCart) {
        Optional<ProductInCart> found = productInCartRepository.findById(productInCart.getId());
        found.ifPresent(inCart -> productInCart.setAmount(productInCart.getAmount() + inCart.getAmount()));
        return productInCartRepository.save(productInCart);
    }

    public ProductInCart delete(long userId, long productId) {
        Optional<ProductInCart> found = productInCartRepository.findByUserIdAndProductId(userId, productId);
        found.ifPresent(productInCartRepository::delete);
        return found.orElseGet(found::get);
    }

    public User findUser(Authentication authentication) {
        return userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new NoSuchElementException("User " + authentication.getName() + " not found."));
    }

    public Product findProduct(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id [" + id + "] not found."));
    }
}
