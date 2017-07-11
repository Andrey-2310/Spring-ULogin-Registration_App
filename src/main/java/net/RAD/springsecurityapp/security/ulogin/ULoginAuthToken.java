package net.RAD.springsecurityapp.security.ulogin;

import net.RAD.springsecurityapp.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ULoginAuthToken extends AbstractAuthenticationToken {
    private String token;
    public User uLoginUser;

    public ULoginAuthToken(String token) {
        super(null);
        this.token = token;
    }

    public User getuLoginUser() {
        return uLoginUser;
    }

    public void setuLoginUser(User uLoginUser) {
        this.uLoginUser = uLoginUser;
    }

    public String getToken() {
        return token;
    }

    public Object getCredentials() {
        return token;
    }


    public Object getPrincipal() {
        return uLoginUser;
    }

    @Override
    public String getName() {
        return uLoginUser.getUsername();
    }
}

