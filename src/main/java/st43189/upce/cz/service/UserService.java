package st43189.upce.cz.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import st43189.upce.cz.entity.User;
import st43189.upce.cz.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User find(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id [" + id + "] not found."));
    }

    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }

    public User delete(long id) {
        Optional<User> found = userRepository.findById(id);
        found.ifPresent(userRepository::delete);
        return found.orElseGet(found::get);
    }

    public String encode(String input) {
        return bCryptPasswordEncoder.encode(input);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
