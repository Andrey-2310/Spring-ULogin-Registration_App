package net.RAD.springsecurityapp.mail;

import net.RAD.springsecurityapp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

/**
 * Created by Андрей on 05.07.2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event)  {
        String token = generateToken();
        verificationTokenService.createVerificationToken(event.getUser(), token);
        URL confirmationUrl = null;
        try {
            confirmationUrl = new URL("http://localhost:8080" +event.getAppUrl() + "/confSuccess?token=" + token);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        MimeMessage email = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(email, true);
            helper.setTo(event.getUser().getEmail());
            helper.setSubject("Registration Confirmation");
            helper.setFrom("redkovskiyandrey@gmail.com");
            helper.setText("<html><body><p>Congratulations. To confirm registration follow <a href=\""+
                    confirmationUrl +"\">this link</a></p></body></html>", true);
            mailSender.send(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(130, secureRandom).toString(32);
    }
}
