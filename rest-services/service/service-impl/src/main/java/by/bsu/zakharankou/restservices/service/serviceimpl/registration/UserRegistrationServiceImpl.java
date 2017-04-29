package by.bsu.zakharankou.restservices.service.serviceimpl.registration;

import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.service.serviceapi.registration.UserRegistrationService;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserDetails;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import by.bsu.zakharankou.restservices.service.serviceimpl.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ReadOnlyTransactional
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public User addUser(UserDetails userDetails) {
        validateUserDetails(userDetails);

        return userService.addSelfRegisteredUser(userDetails);
    }

    private void validateUserDetails(UserDetails userDetails) {
        userValidator.validateUsername(userDetails.getUsername());
        userValidator.validatePasswords(userDetails.getPassword(), userDetails.getPasswordConfirm());
        userValidator.validatePassword(userDetails.getPassword());
    }
}
