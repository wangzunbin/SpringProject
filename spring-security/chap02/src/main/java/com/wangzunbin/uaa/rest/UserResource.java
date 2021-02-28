package com.wangzunbin.uaa.rest;

import org.springframework.http.HttpStatus;
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

/**
 * ClassName:UserResource  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/22 23:26   <br/>
 */

@RestController
@RequestMapping("/api")
public class UserResource {

    @GetMapping("/greeting")
    public String greeting(){
        return "hello World";
    }

    @PostMapping("/greeting")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveGreeting(@RequestParam String name, @RequestBody Profile profile){
        return "hello World:" + name + "\n" + profile.gender;
    }

    @PutMapping("/greeting/{name}")
    public String saveGreeting(@PathVariable String name){
        return "hello World:" + name;
    }
    @Data
    public static class Profile{
        String gender;
        String idNo;
    }
}
