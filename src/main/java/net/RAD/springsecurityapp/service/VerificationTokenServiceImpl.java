package net.RAD.springsecurityapp.service;

import net.RAD.springsecurityapp.repository.VerificationTokenRepository;
import net.RAD.springsecurityapp.model.User;
import net.RAD.springsecurityapp.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by Андрей on 07.07.2017.
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public void createVerificationToken(User user, String token) {
        tokenRepository.save( new VerificationToken(token, user));
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
}
