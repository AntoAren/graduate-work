package by.bsu.zakharankou.restservices.controller.registration;

import by.bsu.zakharankou.restservices.controller.util.JsonResponseEntityFactory;
import by.bsu.zakharankou.restservices.controller.util.ObjectMapper;
import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.service.serviceapi.registration.UserRegistrationService;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Map;

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.INFO_USER_HAS_BEEN_ADDED;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Controller that contain methods to perform user registration.
 */
@Controller
@RequestMapping("/user-registration")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private JsonResponseEntityFactory jsonResponseEntityFactory;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody String body) {
        final Map<String, Object> details = ObjectMapper.read(body);

        details.put(UserDetails.KEY_ROLES, Arrays.asList(new String[]{Authority.ROLE_SIMPLE_USER}));
        details.put(UserDetails.KEY_DISPLAY_NAME, details.get(UserDetails.KEY_USERNAME));
        UserDetails userDetails = new UserDetails(details);

        final User user = userRegistrationService.addUser(userDetails);

        return jsonResponseEntityFactory.createMessageResponse(format(INFO_USER_HAS_BEEN_ADDED, user.getUsername()), CREATED);
    }
}
