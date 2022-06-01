package st43189.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import st43189.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<st43189.entity.User> found = userRepository.findByEmail(email);
        if (found.isPresent())
            if ("st43189@upce.cz".equals(email))
                return new User(email, found.get().getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            else
                return new User(email, found.get().getPassword(), new ArrayList<>());

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
