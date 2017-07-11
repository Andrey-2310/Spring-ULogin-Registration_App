package net.RAD.springsecurityapp.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
/**
 * Simple JavaBean domain object that represents a User.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
