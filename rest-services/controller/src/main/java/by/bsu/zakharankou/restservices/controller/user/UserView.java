package by.bsu.zakharankou.restservices.controller.user;

import by.bsu.zakharankou.restservices.controller.authority.AuthorityView;
import by.bsu.zakharankou.restservices.model.user.User;

import java.util.List;

/**
 * Defines user details.
 */
public class UserView {

    private final String username;
    private final String displayName;
    private final List<AuthorityView> authorities;

    public UserView(final User user, final List<AuthorityView> authorityViews) {
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.authorities = authorityViews;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<AuthorityView> getAuthorities() {
        return authorities;
    }
}
