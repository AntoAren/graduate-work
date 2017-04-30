package by.bsu.zakharankou.restservices.controller.user;

import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST controller for User management
 * supports get method
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get user by username.
     *
     * @param username username
     * @return view with user details
     */
    @Deprecated
    @RequestMapping(value = "/{username:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserView getUser(@PathVariable String username) {
        final User user = userService.getUser(username);
        return UserViewBuilder.build(user);
    }
}
