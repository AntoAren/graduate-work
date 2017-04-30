package by.bsu.zakharankou.restservices.service.serviceimpl.user;

import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.repository.user.UserRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.AuthorityService;
import by.bsu.zakharankou.restservices.service.serviceapi.EntityNotFoundException;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserDetails;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * Implementation of {@link UserService}.
 */
@ReadOnlyTransactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String username) {
        if (!hasText(username)) {
            throw new EntityNotFoundException(ERROR_USER_NAME_EMPTY);
        }
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(String.format(ERROR_USER_NOT_FOUND, username));
        }
        return user;
    }

    @Override
    public User addUser(UserDetails userDetails) {
        return null;
    }

    @Override
    public User addSelfRegisteredUser(UserDetails userDetails) {
        return createUser(userDetails);
    }

    @Override
    public User deleteUser(String username) {
        return null;
    }

    @Override
    public String getAuthenticatedUser() {
        return null;
    }

    @Override
    public boolean exists(String username) {
        return false;
    }

    private User createUser(UserDetails userDetails) {
        Assert.notNull(userDetails, ERROR_USER_DETAILS_NULL);

        final String username = userDetails.getUsername();
        final String displayName = userDetails.getDisplayName();
        final String password = userDetails.getPassword();
        final List<String> roles = userDetails.getRoles();
        final Set<Authority> authorities = new HashSet<>();

        userValidator.validateUsername(username);
        userValidator.validateDisplayName(displayName);
        userValidator.validatePassword(password);
        userValidator.validateRoles(roles);

        final String encodedPassword = passwordEncoder.encodePassword(password, "iTest");
        for (final String role : roles) {
            final Authority authority = authorityService.getAuthority(role);
            authorities.add(authority);
        }

        User user = new User(username.toLowerCase(), displayName, encodedPassword, "iTest");
        user.setAuthorities(authorities);

        user = userRepository.save(user);

        return user;
    }

    @Override
    public User getUser(final String username, final String password) {
        if (!hasText(username)) {
            throw new EntityNotFoundException(ERROR_USER_NAME_EMPTY);
        }
        if (!hasText(password)) {
            throw new EntityNotFoundException(String.format(ERROR_USER_NOT_FOUND, username));
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(ERROR_INVALID_USERNAME_PASSWORD);
        }

        String expectedPassword = passwordEncoder.encodePassword(password, user.getSalt());
        if (!user.getPassword().equals(expectedPassword)) {
            throw new EntityNotFoundException(ERROR_INVALID_USERNAME_PASSWORD);
        }

        return user;
    }
}