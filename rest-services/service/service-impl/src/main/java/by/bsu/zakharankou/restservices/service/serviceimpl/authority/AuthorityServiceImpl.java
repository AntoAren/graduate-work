package by.bsu.zakharankou.restservices.service.serviceimpl.authority;

import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.service.serviceapi.AuthorityService;
import by.bsu.zakharankou.restservices.repository.authority.AuthorityRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.EntityNotFoundException;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.ERROR_AUTHORITY_NAME_EMPTY;
import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.ERROR_AUTHORITY_NOT_FOUND;
import static org.springframework.util.StringUtils.hasText;

/**
 * Implementation of {@link AuthorityService}.
 */
@ReadOnlyTransactional
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<Authority> getAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority getAuthority(final String name) {
        if (!hasText(name)) {
            throw new EntityNotFoundException(ERROR_AUTHORITY_NAME_EMPTY);
        }
        final Authority authority = authorityRepository.findOne(name.trim());
        if (authority == null) {
            throw new EntityNotFoundException(String.format(ERROR_AUTHORITY_NOT_FOUND, name));
        }
        return authority;
    }
}