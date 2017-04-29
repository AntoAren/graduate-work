package by.bsu.zakharankou.restservices.repository.user;

import by.bsu.zakharankou.restservices.model.user.User;

/**
 * Interface for custom operations on a repository for a {@link User}.
 */
public interface UserRepositoryCustom {

    /**
     * Check whether user with the given name exists.
     *
     * @param username user's name.
     * @return true if user exists, otherwise - false
     */
    boolean whetherExists(String username);

    /**
     * Check whether user with the given name and password exists.
     *
     * @param username user's name.
     * @param password user's password.
     * @return true if user exists, otherwise - false
     */
    boolean whetherExists(String username, String password);
}
