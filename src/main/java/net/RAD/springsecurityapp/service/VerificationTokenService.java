package net.RAD.springsecurityapp.service;

import net.RAD.springsecurityapp.model.User;
import net.RAD.springsecurityapp.model.VerificationToken;

/**
 * Created by Андрей on 07.07.2017.
 */
public interface VerificationTokenService {
    User getUser(String verificationToken);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
