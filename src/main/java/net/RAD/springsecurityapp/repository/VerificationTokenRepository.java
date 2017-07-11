package net.RAD.springsecurityapp.repository;

import net.RAD.springsecurityapp.model.User;
import net.RAD.springsecurityapp.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Андрей on 05.07.2017.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
