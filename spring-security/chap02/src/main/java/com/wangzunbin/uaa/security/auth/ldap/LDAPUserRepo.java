package com.wangzunbin.uaa.security.auth.ldap;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ClassName:LDAPUserRepo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 17:35
 */

@Repository
public interface LDAPUserRepo extends LdapRepository<LDAPUser> {

    Optional<LDAPUser> findByUsername(String username);

    Optional<LDAPUser> findByUsernameAndPassword(String username, String password);

    List<LDAPUser> findByUsernameLikeIgnoreCase(String username);
}
