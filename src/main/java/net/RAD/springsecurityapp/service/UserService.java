package net.RAD.springsecurityapp.service;

import net.RAD.springsecurityapp.model.User;

/**
 * Service class for {@link net.RAD.springsecurityapp.model.User}
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

public interface UserService {

    void save(User user);

    User findByUsername(String username);
    User findByEmail(String email);
    User findByPassword(String password);
    String encodePassword(String password);

}
