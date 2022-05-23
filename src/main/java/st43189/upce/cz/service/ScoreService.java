package st43189.upce.cz.service;

import org.springframework.stereotype.Service;
import st43189.upce.cz.entity.Product;
import st43189.upce.cz.entity.ProductInCart;
import st43189.upce.cz.entity.Score;
import st43189.upce.cz.entity.User;
import st43189.upce.cz.repository.ProductRepository;
import st43189.upce.cz.repository.ScoreRepository;
import st43189.upce.cz.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ScoreService(ScoreRepository scoreRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public Score find(long userId, long productId) {
        return scoreRepository
                .findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NoSuchElementException("Score with user id [" + userId + "] and product id [" + productId + "] not found."));
    }

    public Score createOrUpdate(Score score) {
        return scoreRepository.save(score);
    }

    public Score delete(long userId, long productId) {
        Optional<Score> found = scoreRepository.findByUserIdAndProductId(userId, productId);
        found.ifPresent(scoreRepository::delete);
        return found.orElseGet(found::get);
    }

    public User findUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id [" + id + "] not found."));
    }

    public Product findProduct(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id [" + id + "] not found."));
    }
}



