package st43189.upce.cz.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import st43189.upce.cz.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<st43189.upce.cz.entity.User> found = userRepository.findByEmail(email);
        if (found.isPresent())
            return new User(email, found.get().getPassword(), new ArrayList<>());

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
