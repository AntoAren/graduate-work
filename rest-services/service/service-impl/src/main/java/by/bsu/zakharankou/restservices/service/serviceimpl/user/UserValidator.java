package by.bsu.zakharankou.restservices.service.serviceimpl.user;

import by.bsu.zakharankou.restservices.repository.user.UserRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.EntityAlreadyExistsException;
import by.bsu.zakharankou.restservices.service.serviceapi.EntityValidationException;
import by.bsu.zakharankou.restservices.service.serviceimpl.EntityValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.*;
import static org.springframework.util.StringUtils.hasText;

@Component
public class UserValidator extends EntityValidator {

    private static final int USER_NAME_MAX_LENGTH = 50;
    private static final int USER_DISPLAY_NAME_MAX_LENGTH = 50;
    private static final Pattern SIMPLE_ALLOWED_CHARACTERS_PATTERN = Pattern.compile("(\\w|\\.|@)*");

    @Autowired
    private UserRepository userRepository;

    public void validateUsername(final String username) {
        if (!hasText(username)) {
            throw new EntityValidationException(ERROR_USER_NAME_EMPTY);
        }
        if (!EmailValidator.getInstance().isValid(username)) {
            throw new EntityValidationException(ERROR_USER_NAME_MUST_BE_EMAIL);
        }
        if (username.length() > USER_NAME_MAX_LENGTH) {
            throw new EntityValidationException(String.format(ERROR_USER_NAME_MAX_SIZE, USER_NAME_MAX_LENGTH));
        }
//        if (userRepository.whetherExists(username)) {
//            throw new EntityAlreadyExistsException(String.format(ERROR_USER_ALREADY_EXISTS, username));
//        }
    }

    public void validateDisplayName(final String displayName) {
        if (!hasText(displayName)) {
            throw new EntityValidationException(ERROR_USER_DISPLAY_NAME_EMPTY);
        }
        if (displayName.length() > USER_DISPLAY_NAME_MAX_LENGTH) {
            throw new EntityValidationException(String.format(ERROR_USER_DISPLAY_NAME_MAX_SIZE, USER_DISPLAY_NAME_MAX_LENGTH));
        }
    }

    public void validatePassword(final String password) {
        if (!hasText(password)) {
            throw new EntityValidationException(ERROR_USER_PASSWORD_EMPTY);
        }
        if (!SIMPLE_ALLOWED_CHARACTERS_PATTERN.matcher(password).matches()) {
            throw new EntityValidationException(ERROR_USER_PASSWORD_INVALID_CHARACTERS);
        }
    }

    public void validatePasswords(final String password, final String passwordConfirm) {
        //If one of the passwords is valid, it's necessary to check only their equality.
        validatePassword(password);
        if (!password.equals(passwordConfirm)) {
            throw new EntityValidationException(ERROR_USER_PASSWORDS_NOT_MATCH);
        }
    }

    public void validateRoles(final List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new EntityValidationException(ERROR_USER_ROLES_EMPTY);
        }
    }

    //TODO: check it
    public boolean isValidUsername(final String username) {
        boolean result = true;

//        if (!hasText(username) || !EmailValidator.getInstance().isValid(username)
//                || username.length() > USER_NAME_MAX_LENGTH
//                || !
//                userRepository.whetherExists(username)) {
//                result = false;
//            }

        return result;
    }
}
