package com.wzb.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wzb.dto.User;
import com.wzb.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:UserController  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 20:59   <br/>
 */

@RestController
@RequestMapping("/user")
public class UserController {


//    @GetMapping("/me")
//    public Object getCurrentUser(){
    // SecurityContextHolder底层就是一个Threadlocal
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//  @GetMapping("/me")
//    public Object getCurrentUser(Authentication authentication){
//        return authentication;
//    }
    // 只获取用户的信息
    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails authentication){
        return authentication;
    }


    @GetMapping
    @ApiOperation("用户查询")
//    public List<User> query(@RequestParam String username){
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 2, size = 1454, sort = "username asc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }


    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam("用户id") @PathVariable String id){
//        throw new RuntimeException("user not exist");
        User user = new User();
        user.setUsername("wzb");
        return user;
    }

    @PostMapping
    public User create(@RequestBody User user){
        System.out.println(user);
        System.out.println(user.getBirthDay());
        user.setId(BigInteger.ONE.intValue());
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors){
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(s -> System.out.println(s.getDefaultMessage()));
        }
        System.out.println(user);
        System.out.println(user.getBirthDay());
        user.setId(BigInteger.ONE.intValue());
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);

    }
}

