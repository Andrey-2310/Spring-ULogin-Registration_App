package net.RAD.springsecurityapp.security.ulogin;

import net.RAD.springsecurityapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Андрей on 09.07.2017.
 */
public class UloginAuthentificationProvider {
    private static final Logger LOG= LoggerFactory.getLogger(UloginAuthentificationProvider.class);

    /**
     * Домен сайта, который производит аутентификацию.
     */
    private String host;

    public UloginAuthentificationProvider(String host) {
        this.host=host;
    }


    public ULoginAuthToken authenticate(ULoginAuthToken uLoginAuthToken) throws AuthenticationException {
        if (!supports(uLoginAuthToken.getClass())) {
            return null;
        }

        try {
            URL uloginUrl = new URL("http://ulogin.ru/token.php?token=" + uLoginAuthToken.getCredentials() + "&host="+host);
            URLConnection urlConnection = uloginUrl.openConnection();

            JsonReader jsonReader = Json.createReader(urlConnection.getInputStream());
            JsonObject obj = jsonReader.readObject();

            if (obj == null ) {
                throw new BadCredentialsException("ulogin did't return json object");
            }
            if(obj.getJsonString("identity")==null){
                throw new BadCredentialsException("ulogin did't return identity object");
            }

            String identity = obj.getJsonString("identity").getString();
            LOG.info(identity);

            User uLoginUser = new User();

            uLoginUser.setUsername(getStringProp(obj,"first_name"));
            uLoginUser.setEmail(getStringProp(obj,"email"));
            uLoginUser.setPassword(getStringProp(obj, "identity"));
            uLoginUser.setEnabled(true);

            uLoginAuthToken.setuLoginUser(uLoginUser);
            uLoginAuthToken.setAuthenticated(true);

        }catch (Exception ex){
            uLoginAuthToken.setAuthenticated(false);
            LOG.error(ex.getMessage(),ex);
            throw new AuthenticationServiceException(ex.getMessage());
        }
        return uLoginAuthToken;
        
    }


    private String getStringProp(JsonObject obj, String prop) {
        JsonString jsonString = obj.getJsonString(prop);
        if(jsonString==null){
            return null;
        }
        return jsonString.getString();
    }


    public boolean supports(Class<?> authentication) {
        return ULoginAuthToken.class.isAssignableFrom(authentication);
    }
}
