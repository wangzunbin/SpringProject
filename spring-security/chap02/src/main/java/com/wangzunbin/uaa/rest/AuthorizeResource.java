package com.wangzunbin.uaa.rest;

import com.wangzunbin.uaa.domain.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public User register(@RequestBody @Valid User userDto) {
        return userDto;
    }

}
