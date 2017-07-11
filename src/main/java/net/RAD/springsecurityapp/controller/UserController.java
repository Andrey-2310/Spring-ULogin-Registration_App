package net.RAD.springsecurityapp.controller;

import net.RAD.springsecurityapp.mail.OnRegistrationCompleteEvent;
import net.RAD.springsecurityapp.model.User;
import net.RAD.springsecurityapp.model.VerificationToken;
import net.RAD.springsecurityapp.security.ulogin.UloginAuthenticationFilter;
import net.RAD.springsecurityapp.service.SecurityService;
import net.RAD.springsecurityapp.service.UserService;
import net.RAD.springsecurityapp.service.VerificationTokenService;
import net.RAD.springsecurityapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller for {@link net.RAD.springsecurityapp.model.User}'s pages.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private
    ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, WebRequest request) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userForm.setPassword(userService.encodePassword(userForm.getPassword()));
        userService.save(userForm);

        // securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (userForm, request.getLocale(), request.getContextPath()));
        } catch (Exception me) {
            me.printStackTrace();
            return "emailError";
        }
        model.addAttribute("username", userForm.getUsername());
        return "regSuccess";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@ModelAttribute("userForm") User userForm, Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }


    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(WebRequest request, String error) {
        return "welcome";
    }


    @RequestMapping(value = "/confSuccess", method = RequestMethod.GET)
    public String confSuccess(Model model, @RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null)
            return "emailError";
        User user = verificationToken.getUser();
        user.setEnabled(true);
        //securityService.autoLogin(user.getUsername(), user.getConfirmPassword());
        userService.save(user);
        model.addAttribute("username", user.getUsername());
        return "welcome";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping(value = "/checkAuthorization", method = RequestMethod.POST)
    public String checkAuthorization(WebRequest request, Model model) {
        User user = UloginAuthenticationFilter.attemptAuthentication(request);
        if (user.isEnabled()) {
            model.addAttribute("username", user.getUsername());
            if (userService.findByPassword(user.getPassword()) == null)
                userService.save(user);
            return "welcome";
        }
        return "error";
    }
}
