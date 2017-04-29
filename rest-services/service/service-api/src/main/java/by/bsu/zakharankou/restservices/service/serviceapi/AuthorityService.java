package by.bsu.zakharankou.restservices.service.serviceapi;

import by.bsu.zakharankou.restservices.model.authority.Authority;

import java.util.List;

public interface AuthorityService {

    /**
     * Get list of authorities.
     *
     * @return list of authorities
     */
    List<Authority> getAuthorities();

    /**
     * Get authority by name.
     *
     * @param name authority name
     * @return authority
     */
    Authority getAuthority(String name);
}
