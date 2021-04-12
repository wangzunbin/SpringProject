package com.wangzunbin.uaa.rest;

import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * ClassName:UserResource  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/22 23:26   <br/>
 */

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping("/greeting")
    public String greeting() {
        return "hello World";
    }

    @PostMapping("/greeting")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveGreeting(@RequestParam String name, @RequestBody Profile profile) {
        return "hello World:" + name + "\n" + profile.gender;
    }

    @PutMapping("/greeting/{name}")
    public String saveGreeting(@PathVariable String name) {
        return "hello World:" + name;
    }

    @GetMapping("/principal")
    public String getPrincipal() {
        // SecurityContextHolder里面底层就是用的是ThreadLocal存储
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/principal/authentication")
    public Authentication getAuthentication() {
        // SecurityContextHolder里面底层就是用的是ThreadLocal存储
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/users/{username}")
    public String getCurrentUsername(@PathVariable String username) {
        return username;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostAuthorize("returnObject.username == authentication.name")
    @GetMapping("/users/by-email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findOptionalByEmail(email).orElseThrow();
    }

    @GetMapping("/users/manager")
    public String getManagerResource() {
        return "hi";
    }

    @Data
    public static class Profile {
        String gender;
        String idNo;
    }
}
