package st43189.repository;

import st43189.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByName(String name);
    boolean existsUserByEmail(String email);

    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
