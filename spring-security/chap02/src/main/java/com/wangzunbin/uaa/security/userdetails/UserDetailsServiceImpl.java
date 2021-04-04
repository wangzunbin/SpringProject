package com.wangzunbin.uaa.security.userdetails;

import com.wangzunbin.uaa.repository.UserRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:UserDetailsServiceImpl
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 10:07
 */

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findOptionalByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Cannot find username"));
    }
}
