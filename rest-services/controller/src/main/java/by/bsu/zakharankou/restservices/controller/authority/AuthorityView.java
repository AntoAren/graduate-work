package by.bsu.zakharankou.restservices.controller.authority;

import by.bsu.zakharankou.restservices.model.authority.Authority;

/**
 * Defines authority.
 */
public class AuthorityView {

    private final String name;
    private final String displayName;

    public AuthorityView(final Authority authority) {
        this.name = authority.getName();
        this.displayName = authority.getDisplayName();
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
