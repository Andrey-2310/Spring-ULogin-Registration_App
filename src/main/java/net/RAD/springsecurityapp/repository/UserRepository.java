package net.RAD.springsecurityapp.repository;

import net.RAD.springsecurityapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPassword(String password);
}
