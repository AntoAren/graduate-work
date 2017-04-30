package by.bsu.zakharankou.restservices.service.serviceapi.user;

import by.bsu.zakharankou.restservices.model.user.User;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * An interface with methods for manipulating users.
 */
public interface UserService {

    /**
     * Finds user by name. Search by name is case insensitive.
     *
     * @param username username
     * @return user
     */
    User getUser(String username);

    User addUser(UserDetails userDetails);

    User getUser(String username, String password);

    User addSelfRegisteredUser(UserDetails userDetails);

    /**
     * Deletes user by its name.
     *
     * @param username user's name
     */
    @PreAuthorize("@userPermissionManager.hasPermission(T(Permission).DELETE_ANY)")
    User deleteUser(String username);

    /**
     * Gets currently authenticated user name.
     *
     * @return authenticated user name.
     */
    String getAuthenticatedUser();

    /**
     * Checks whether user with the given username exists
     *
     * @param username user name
     * @return true if user with the given username exists and false otherwise
     */
    boolean exists(String username);
}
