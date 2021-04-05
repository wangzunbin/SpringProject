package com.wangzunbin.uaa.rest;

import com.wangzunbin.uaa.domain.Auth;
import com.wangzunbin.uaa.domain.LoginDto;
import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.service.UserService;
import com.wangzunbin.uaa.util.JWTUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * ClassName:AuthorizeResource  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:30   <br/>
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    private final UserService userService;
    private final static String PREFIX = "Bearer";
    private final JWTUtil jwtUtil;

    @PostMapping(value="/register")
    public User register(@RequestBody @Valid User userDto) {
        return userDto;
    }

    @PostMapping("/token")
    public Auth login(@RequestBody @Valid LoginDto loginDto) throws Exception{
        return userService.login(loginDto.getUsername(), loginDto.getPassword());
    }


    @PostMapping("/token/refresh")
    public Auth refreshToken(@RequestHeader(name = "Authorization")String authorization, @RequestParam String refreshToken) throws AccessDeniedException{
        val accessToken = authorization.replace(PREFIX, "");
        if(jwtUtil.validateRefreshToken(refreshToken) && jwtUtil.validateAccessTokenWithoutExpiration(accessToken)) {
            return new Auth(jwtUtil.createAccessTokenWithRefreshToken(refreshToken), refreshToken);
        }
        throw new AccessDeniedException("访问被拒绝");
    }
}
