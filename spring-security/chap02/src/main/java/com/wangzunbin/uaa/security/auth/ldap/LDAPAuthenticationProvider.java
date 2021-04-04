package com.wangzunbin.uaa.security.auth.ldap;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:LDAPAuthenticationProvider
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 18:24
 */

@RequiredArgsConstructor
public class LDAPAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final LDAPUserRepo ldapUserRepo;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 忽略
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return ldapUserRepo.findByUsernameAndPassword(username, authentication.getCredentials().toString()).orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
    }
}
