package com.wangzunbin.uaa.security.userdetails;

import com.wangzunbin.uaa.repository.UserRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:UserDetailsPasswordServiceImpl
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 10:35
 */


@RequiredArgsConstructor
@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserRepo userRepo;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return userRepo.findOptionalByUsername(user.getUsername())
                .map(userFromDb -> userRepo.save(userFromDb.withPassword(newPassword)))
                .orElseThrow();
    }
}
