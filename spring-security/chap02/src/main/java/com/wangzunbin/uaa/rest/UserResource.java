package com.wangzunbin.uaa.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
