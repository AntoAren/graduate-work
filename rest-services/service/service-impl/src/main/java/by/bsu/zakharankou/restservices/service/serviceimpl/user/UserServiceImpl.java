package by.bsu.zakharankou.restservices.service.serviceimpl.user;

import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.repository.user.UserRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.AuthorityService;
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

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.ERROR_USER_DETAILS_NULL;

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
        return null;
    }

    @Override
    public User addUser(UserDetails userDetails) {
        return null;
    }

    @Override
    public User addSelfRegisteredUser(UserDetails userDetails) {

        return null;
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
}