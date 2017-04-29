package by.bsu.zakharankou.restservices.repository.authority;

import by.bsu.zakharankou.restservices.model.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for operations on a repository for a {@link Authority}.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
