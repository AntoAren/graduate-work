package by.bsu.zakharankou.restservices.model.authority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {

    public static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_APPSTORE_ADMIN = "ROLE_APPSTORE_ADMIN";
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String ROLE_SALES = "ROLE_SALES";

    @Id
    private String name;
    private String displayName;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
