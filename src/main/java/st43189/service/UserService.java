package st43189.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import st43189.entity.User;
import st43189.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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

    public User find(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with email [" + email + "] not found."));
    }

    public User createOrUpdate(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createOrUpdate(User user, Authentication authentication, String oldPassword) {
        Optional<User> logged = userRepository.findByEmail(authentication.getName());
        if (logged.isPresent()) {
            User loggedUser = logged.get();
            user.setId(loggedUser.getId());
            user.setEmail(loggedUser.getEmail());
            if (Objects.equals(user.getPassword(), "") || !bCryptPasswordEncoder.matches(oldPassword, loggedUser.getPassword()))
                user.setPassword(loggedUser.getPassword());
            else
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User delete(long id) {
        Optional<User> found = userRepository.findById(id);
        found.ifPresent(userRepository::delete);
        return found.orElseGet(found::get);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
