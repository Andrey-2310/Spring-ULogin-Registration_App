package net.RAD.springsecurityapp.validator;

import net.RAD.springsecurityapp.model.User;
import net.RAD.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Validator for {@link net.RAD.springsecurityapp.model.User} class,
 * implements {@link Validator} interface.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        validateUsername(user.getUsername(), errors);
        validatePassword(user.getPassword(), errors);
        validatePasswordConfirmation(user.getPassword(), user.getConfirmPassword(), errors);
        validateEmail(user.getEmail(), errors);
    }

    private void validateUsername(String username, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (username.length() < 8 || username.length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(username) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
    }

    private void validatePassword(String password, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (password.length() < 8 || password.length() > 32)
            errors.rejectValue("password", "Size.userForm.password");
        if(userService.findByPassword(password)!=null)
            errors.rejectValue("password", "Duplicate.userForm.password");

    }

    private void validatePasswordConfirmation(String passwordConfirmation, String password, Errors errors) {
        if (!passwordConfirmation.equals(password)) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }

    private void validateEmail(String email, Errors errors) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email))
            errors.rejectValue("email", "Invalid.userForm.email");
        if(userService.findByEmail(email)!=null)
            errors.rejectValue("email", "Duplicate.userForm.email");
    }
}
