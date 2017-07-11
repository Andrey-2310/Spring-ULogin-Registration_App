package net.RAD.springsecurityapp.security.ulogin;

import net.RAD.springsecurityapp.model.User;
import org.springframework.web.context.request.WebRequest;

/**
 * Получает ulogin token из запроса.
 */
public class UloginAuthenticationFilter {

    public static User attemptAuthentication(WebRequest request) {

        String token = request.getParameterValues("token")[0];
        UloginAuthentificationProvider provider = new UloginAuthentificationProvider(request.getHeader("host"));
        ULoginAuthToken authRequest = new ULoginAuthToken(token);
        return provider.authenticate(authRequest).getuLoginUser();
    }
}
