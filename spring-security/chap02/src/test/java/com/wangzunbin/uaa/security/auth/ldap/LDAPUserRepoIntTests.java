package com.wangzunbin.uaa.security.auth.ldap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ClassName:LDAPUserRepoIntTests
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 17:37
 */

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DataLdapTest
@Slf4j
public class LDAPUserRepoIntTests {

    @Autowired
    private LDAPUserRepo ldapUserRepo;

    @Test
    public void givenUsername_ThenFindUserSuccess() {
        val user = ldapUserRepo.findByUsername("zhaoliu");
        assertTrue(user.isPresent());
    }

    @Test
    public void givenUsernameAndPassword_ThenFindUserSuccess() {
        val user = ldapUserRepo.findByUsernameAndPassword("zhaoliu", "123");
        log.debug(user.toString());
        assertTrue(user.isPresent());
    }

    @Test
    public void givenUsernameAndWrongPassword_ThenFindUserFail() {
        val user = ldapUserRepo.findByUsernameAndPassword("zhaoliu", "bad_password");
        assertTrue(user.isEmpty());
    }
}
