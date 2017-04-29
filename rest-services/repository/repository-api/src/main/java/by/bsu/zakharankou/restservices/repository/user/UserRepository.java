package by.bsu.zakharankou.restservices.repository.user;

import by.bsu.zakharankou.restservices.model.authority.Authority;
import by.bsu.zakharankou.restservices.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface for operations on a repository for a {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    /**
//     * Finds user by its name.
//     *
//     * @param username user's name
//     * @return user
//     */
//    User findByUsername(String username);
//
//    /**
//     * Finds user by its name and password.
//     *
//     * @param username user's name
//     * @param password user's password
//     * @return user
//     */
//    User findByUsernameAndPassword(String username, String password);
//
//    /**
//     * Finds users whose name or display name start with text.
//     *
//     * @param username    user name text
//     * @param displayName display name text
//     * @param pageable    pagination information
//     * @return users list
//     */
//    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',CONCAT(REPLACE(REPLACE(:username, '%', '\\%'), '_', '\\_'),'%')) OR LOWER(u.displayName) LIKE LOWER(CONCAT('%',CONCAT(REPLACE(REPLACE(:displayName, '%', '\\%'), '_', '\\_'),'%')))")
//    Page<User> findByUsernameOrDisplayName(@Param("username") String username, @Param("displayName") String displayName, Pageable pageable);
//
//    /**
//     * Finds users who have appropriate authority and whose name or display name
//     * start with text.
//     *
//     * @param userAuthority user authority
//     * @param username      user name text
//     * @param displayName   display name text
//     * @param pageable      pagination information
//     * @return users list
//     */
//    @Query("SELECT u FROM User u WHERE :userAuthority MEMBER OF u.authorities "
//            + "AND (u.username LIKE CONCAT(REPLACE(REPLACE(:username, '%', '\\%'), '_', '\\_'),'%') "
//            + "OR LOWER(u.displayName) LIKE LOWER(CONCAT(REPLACE(REPLACE(:displayName, '%', '\\%'), '_', '\\_'),'%')))")
//    Page<User> findByUserAuthorityAndUsernameOrDisplayName(@Param("userAuthority") Authority userAuthority,
//                                                           @Param("username") String username, @Param("displayName") String displayName, Pageable pageable);
//
//    /**
//     * Finds users who have appropriate authority.
//     *
//     * @param userAuthority user authority
//     * @param pageable      pagination information
//     * @return users list
//     */
//    @Query("SELECT u FROM User u WHERE :userAuthority MEMBER OF u.authorities")
//    Page<User> findByUserAuthority(@Param("userAuthority") Authority userAuthority, Pageable pageable);
}
