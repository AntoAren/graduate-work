package by.bsu.zakharankou.restservices.service.serviceapi.user;

import by.bsu.zakharankou.restservices.service.serviceapi.RequestDetails;

import java.util.List;
import java.util.Map;

/**
 * Entity which describes details of user.
 */
public class UserDetails extends RequestDetails {

    public static final String KEY_ORGANIZATION = "organization";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_DISPLAY_NAME = "displayName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PASSWORD_CONFIRM = "passwordConfirm";
    public static final String KEY_ROLES = "roles";
    public static final String KEY_TRKD_CREDENTIALS = "trkdCredentials";

    private String username;
    private String displayName;
    private String password;
    private String passwordConfirm;
    private List<String> roles;

    /**
     * Construct new instance.
     *
     * @param details map of user details. Example: {organization: 'urn:org:tr', username: 'john', displayName: 'john dow',
     *                password: 'welcome', passwordConfirm: 'welcome', roles: ['ROLE_ADMIN', 'ROLE_DEVELOPER']}.
     */
    public UserDetails(final Map<String, Object> details) {
        super(details);
        username = trim(getString(details, KEY_USERNAME));
        displayName = normalizeString(getString(details, KEY_DISPLAY_NAME));
        password = getString(details, KEY_PASSWORD);
        passwordConfirm = getString(details, KEY_PASSWORD_CONFIRM);
        roles = getList(details, KEY_ROLES, String.class);
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public List<String> getRoles() {
        return roles;
    }
}
