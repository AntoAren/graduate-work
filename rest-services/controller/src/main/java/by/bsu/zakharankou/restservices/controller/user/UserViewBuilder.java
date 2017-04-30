package by.bsu.zakharankou.restservices.controller.user;

import by.bsu.zakharankou.restservices.controller.authority.AuthorityView;
import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Helper class to build different views for an user.
 */
class UserViewBuilder {

    /**
     * Build view for the specified user.
     *
     * @param user user for which to create a view
     * @return view
     */
    public static UserView build(final User user) {
        return new UserView(user, buildAuthorities(user.getAuthorities()));
    }

    private static List<AuthorityView> buildAuthorities(final Set<Authority> authorities) {
        final List<AuthorityView> result = new ArrayList<>(authorities.size());
        for (final Authority authority : authorities) {
            final Authority value = new Authority();
            value.setName(authority.getName());
            value.setDisplayName(authority.getDisplayName());
            result.add(new AuthorityView(value));
        }
        return result;
    }

    private static List<String> buildModifiedAuthorities(final Set<Authority> authorities) {
        final List<String> result = new ArrayList<>(authorities.size());
        for (final Authority authority : authorities) {
            result.add(authority.getName());
        }
        return result;
    }
}
