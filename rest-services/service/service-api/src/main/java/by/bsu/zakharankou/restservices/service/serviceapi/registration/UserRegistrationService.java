package by.bsu.zakharankou.restservices.service.serviceapi.registration;

import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserDetails;

public interface UserRegistrationService {

    /**
     * Add new user.
     *
     * @param userDetails details of new user
     * @return new user
     */
    User addUser(UserDetails userDetails);
}
