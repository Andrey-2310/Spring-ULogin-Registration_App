package net.RAD.springsecurityapp.service;

import net.RAD.springsecurityapp.repository.RoleRepository;
import net.RAD.springsecurityapp.repository.UserRepository;
import net.RAD.springsecurityapp.model.Role;
import net.RAD.springsecurityapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email){return userRepository.findByEmail(email);}

    @Override
    public User findByPassword(String password) {return userRepository.findByPassword(password);}

    @Override
    public String encodePassword(String password) {
       return  bCryptPasswordEncoder.encode(password);
    }


}
