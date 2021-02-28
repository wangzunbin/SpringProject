package com.wangzunbin.uaa.rest;

import com.wangzunbin.uaa.domain.UserDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:AuthorizeResource  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:30   <br/>
 */
@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    @PostMapping(value="/register")
    public UserDto register(@RequestBody UserDto userDto) {
        return userDto;
    }

}
