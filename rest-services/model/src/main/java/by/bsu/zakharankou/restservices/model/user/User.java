package by.bsu.zakharankou.restservices.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

import org.springframework.util.StringUtils;

import by.bsu.zakharankou.restservices.model.authority.Authority;

@Entity
public class User {

    public static final String FIELD_USERNAME = "username";

    @Id
    private String username;
    private String displayName;
    private String password;

    private String salt;
    private boolean confirmed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authority_user", joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name")}
    )
    private Set<Authority> authorities;

    public User() {
    }

    public User(String username, String displayName, String password, String salt) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    static Set<String> expandValues(final String values) {
        return StringUtils.commaDelimitedListToSet(values);
    }

    static String collapseValues(final Set<String> values) {
        return StringUtils.collectionToCommaDelimitedString(values);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }
}
